package com.boss.view.activity;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivitySettingBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private final Activity activity = SettingActivity.this;
    private ActivitySettingBinding binding;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(this);

        binding.ivBack.setOnClickListener(view -> onBackPressed());
        binding.logoutLay.setOnClickListener(v -> logout());
        binding.ivDelete.setOnClickListener(v -> deleteAccount());
        binding.aboutUsLay.setOnClickListener(v -> startActivity(new Intent(activity, AboutUsActivity.class)));
        binding.privacyPolLay.setOnClickListener(v -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));
        binding.termsConditionLay.setOnClickListener(v -> startActivity(new Intent(activity, TermsConditionActivity.class)));
        binding.faqLay.setOnClickListener(v -> startActivity(new Intent(activity, FaqActivity.class)));
    }

    private void deleteAccount() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.deleteAccount(session.getUser_Id()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            session.logout();
                            finish();
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yes", (dialogInterface, i) -> {

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser user = auth.getCurrentUser();

                    if (user != null) {
                        auth.signOut();
                    }

                    // LoginManager.getInstance().logOut();

                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);

                    mGoogleSignInClient.signOut();


                    ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.show();
                    ApiService apiService = RetrofitClient.getClient(activity);

                    apiService.logout(session.getUser_Id()).enqueue(new Callback<CommonResModel>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                            progressDialog.dismiss();
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    if (response.body().getResult().equalsIgnoreCase("true")) {
                                        Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        session.logout();
                                        finish();
                                    } else
                                        Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                            progressDialog.dismiss();
                            Log.e(TAG, "" + t.getLocalizedMessage());
                        }
                    });
                })
                .show();
    }
}