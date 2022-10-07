package com.boss.view.activity;

import static com.facebook.FacebookSdk.setAutoLogAppEventsEnabled;
import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivityLoginBinding;
import com.boss.model.Response_Models.LoginModel;
import com.boss.model.SocialLoginResModel;
import com.boss.util.Constants;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1000;
    private final Activity activity = LoginActivity.this;
    GoogleSignInClient googleSignInClient;
    CallbackManager mCallbackManager;
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.white));

        mAuth = FirebaseAuth.getInstance();

        mCallbackManager = CallbackManager.Factory.create();
        session = new Session(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.googleSignup.setOnClickListener(view -> {
            Intent intent = googleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        });

        binding.facebookSignup.setOnClickListener(view -> {
            LoginWithFacebook();
            printHashKey();
            setAutoLogAppEventsEnabled(true);
        });

        binding.tvLogin.setOnClickListener(view -> {
            String mobile = binding.edtMobile.getText().toString();
            if (mobile.length() < 10) {
                binding.edtMobile.setError("Enter a valid Mobile Number");
            } else {
                login(mobile);
            }
        });

        binding.tvSignup.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.enter, R.anim.exit);
            finish();
        });
    }

    private void LoginWithFacebook() {
        binding.facebookSignup.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(ContentValues.TAG, "facebook:onSuccess:" + loginResult.toString());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (AccessToken.getCurrentAccessToken() != null) {
                                    Log.e(ContentValues.TAG, "onCompleted: " + AccessToken.getCurrentAccessToken());
                                    Log.e(ContentValues.TAG, "onCompleted: " + AccessToken.getCurrentAccessToken().getUserId());
                                }
                                if (response.getJSONObject() != null) {
                                    Log.e("SignUpActivity", response.toString());
                                    Log.e("TAG", "onCompleted: " + response.getJSONObject().toString().contains("email"));
                                    try {
                                        String fbId = object.getString("id");
                                        String fbName = object.getString("name");

                                        facebookLogin(fbName, session.getValue(Constants.Key.fcm_id), fbId);
                                        Log.e(ContentValues.TAG, "fbid: " + fbId);
                                        Log.e(ContentValues.TAG, "fbname: " + fbName);

                                        Log.e(ContentValues.TAG, "onCompleted:fbname   " + fbName);

                                        if (!response.getJSONObject().toString().contains("email")) {
                                            Log.e("email_null", "onCompleted: ");
                                        } else {
                                            Log.e("email", "onCompleted: ");
                                            String fb_email = object.getString("email");
                                            Log.e("facebookData", "onCompleted: " + fb_email);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(ContentValues.TAG, "facebook:onCancel");
            }

            @Override
            public void onError(@NonNull FacebookException error) {
                Log.d(ContentValues.TAG, "facebook:onError", error);
            }
        });
    }

    private void printHashKey() {
        // Add code to print out the key hash
        try {

            PackageInfo info = getPackageManager().getPackageInfo("com.stitch.stitchify", PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {

                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN)
            googleSignInResult(data);
    }

    private void googleSignInResult(Intent data) {

        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account = task.getResult();
            Log.i("OnREsponse", "Account Id = " + account.getId());
            AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            mAuth.signInWithCredential(authCredential)
                    .addOnCompleteListener(this, task1 -> {
                        if (task1.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            if (user != null) {
                                Log.d("onResponse", "Username :-" + user.getDisplayName());
                                Log.d("onResponse", "Email :-" + user.getEmail());
                                Log.d("onResponse", "id :-" + user.getUid());

                                String name, email, uId;

                                uId = user.getUid();
                                name = user.getDisplayName();
                                email = user.getEmail();

                                googleLogin(name, uId, email, session.getValue(Constants.Key.fcm_id));
                            } else Log.w("TAG", "signInWithCredential:failure", task1.getException());
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task1.getException());
                        }
                    });
        } catch (Exception e) {
            Log.i("OnError", "Account Id = Not get");
        }
    }

    private void googleLogin(String name, String googleId, String email, String fcmId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.googleLogin(name, email, fcmId, googleId).enqueue(new Callback<SocialLoginResModel>() {
            @Override
            public void onResponse(@NonNull Call<SocialLoginResModel> call, @NonNull Response<SocialLoginResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            startActivity(new Intent(activity, HomeActivity.class)
                                    .putExtra(Constants.Key.user_id, String.valueOf(response.body().getData().getId()))
                                    .putExtra(Constants.Key.otp, String.valueOf(response.body().getData().getOtp())));

                            session.setUser_Id(response.body().getData().getId());
                            session.setValue(Constants.Key.mobile, response.body().getData().getMobile());
                            session.setLogin(true);

                            finish();
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SocialLoginResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void facebookLogin(String name, String fcmId, String facebookId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.facebookLogin(name, fcmId, facebookId).enqueue(new Callback<SocialLoginResModel>() {
            @Override
            public void onResponse(@NonNull Call<SocialLoginResModel> call, @NonNull Response<SocialLoginResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            startActivity(new Intent(activity, HomeActivity.class)
                                    .putExtra(Constants.Key.user_id, String.valueOf(response.body().getData().getId()))
                                    .putExtra(Constants.Key.otp, String.valueOf(response.body().getData().getOtp())));

                            session.setUser_Id(response.body().getData().getId());
                            session.setValue(Constants.Key.mobile, response.body().getData().getMobile());
                            session.setLogin(true);

                            finish();
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SocialLoginResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void login(String mobile) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.login(mobile).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            startActivity(new Intent(activity, OtpVerificationActivity.class)
                                    .putExtra(Constants.Key.user_id, String.valueOf(response.body().getData().getId()))
                                    .putExtra(Constants.Key.mobile, mobile)
                                    .putExtra(Constants.Key.otp, String.valueOf(response.body().getData().getOtp())));
                            finish();
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }
}