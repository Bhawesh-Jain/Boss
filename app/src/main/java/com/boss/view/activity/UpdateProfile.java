package com.boss.view.activity;

import static com.boss.util.BaseUrl.Base_Url;
import static com.boss.util.BaseUrl.update_profile;
import static com.boss.util.ImageShortCut.bitmapToFile;
import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivityUpdateProfileBinding;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.util.Constants;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;
import com.bumptech.glide.Glide;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpdateProfile extends AppCompatActivity implements View.OnClickListener {
    private final Activity activity = UpdateProfile.this;
    private final String TAG = UpdateProfile.class.getSimpleName();
    private ActivityUpdateProfileBinding binding;
    private File imgFile;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        session = new Session(this);

        init();
    }


    private void init() {
        getProfile();

        binding.edtUsername.setText(session.getValue(Constants.Key.user_name));
        binding.edtCompany.setText(session.getValue(Constants.Key.user_company));
        binding.edtBio.setText(session.getValue(Constants.Key.user_about));

        String profileImg = session.getValue(Constants.Key.user_img);
        if (profileImg.length() != 0)
            Glide.with(activity)
                    .load(profileImg)
                    .error(R.drawable.ic_user)
                    .placeholder(R.drawable.ic_user)
                    .into(binding.icUserImage);

        binding.icBack.setOnClickListener(this);
        binding.tvMale.setOnClickListener(this);
        binding.tvFemale.setOnClickListener(this);
        binding.tvOthers.setOnClickListener(this);
        binding.tvChangePhoto.setOnClickListener(this);
        binding.tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.icBack) {
            onBackPressed();
        } else if (view == binding.tvMale) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background_theme);
            binding.tvFemale.setBackgroundResource(R.drawable.curve_background);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background);
        } else if (view == binding.tvFemale) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvFemale.setBackgroundResource(R.drawable.curve_background_theme);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background);
        } else if (view == binding.tvOthers) {
            binding.tvMale.setBackgroundResource(R.drawable.curve_background);
            binding.tvFemale.setBackgroundResource(R.drawable.curve_background);
            binding.tvOthers.setBackgroundResource(R.drawable.curve_background_theme);
        } else if (view == binding.tvSave) {
            String name = binding.edtUsername.getText().toString();
            String bio = binding.edtBio.getText().toString();
            String email = binding.edtEmail.getText().toString();

            if (name.length() == 0)
                binding.edtUsername.setError("Username Cannot be empty!");
            else
                updateProfile(name, bio, email);
        } else if (view == binding.tvChangePhoto) {
            selectImage();
        }
    }

    private void selectImage() {
        final PickImageDialog dialog = PickImageDialog.build(new PickSetup());

        dialog.setOnPickCancel(dialog::dismiss).setOnPickResult(r -> {
            if (r.getError() == null) {
                binding.icUserImage.setImageBitmap(r.getBitmap());
                imgFile = bitmapToFile(activity, r.getBitmap());
            } else {
                Toast.makeText(activity, r.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }).show(this);
    }

    private void getProfile() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getProfile(session.getUser_Id(), session.getUser_Id()).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ProfileModel.Data data = response.body().getData();

                            session.setValue(Constants.Key.user_name, data.getName());
                            session.setValue(Constants.Key.user_company, data.getCompany());
                            session.setValue(Constants.Key.user_about, data.getAbout());

                            binding.edtUsername.setText(data.getName());
                            binding.edtCompany.setText(data.getCompany());
                            binding.edtBio.setText(data.getAbout());

                            if (data.getImage().length() != 0) {
                                session.setValue(Constants.Key.user_img, response.body().getPath() + data.getImage());
                                Glide.with(activity)
                                        .load(response.body().getPath() + data.getImage())
                                        .error(R.drawable.ic_user)
                                        .placeholder(R.drawable.ic_user)
                                        .into(binding.icUserImage);
                            }

                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void updateProfile(String name, String bio, String email) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ANRequest.MultiPartBuilder anReq = AndroidNetworking.upload(Base_Url + update_profile);
        anReq.addMultipartParameter("name", name);
        anReq.addMultipartParameter("bio", bio);
        anReq.addMultipartParameter("email", email);
        anReq.addMultipartParameter("user_id", session.getUser_Id());
        if (imgFile != null) anReq.addMultipartFile("image", imgFile);
        anReq.build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressDialog.dismiss();
                        try {
                            Log.d(TAG, "onResponse() called with: jsonObject = [" + jsonObject + "]");

                            String result = jsonObject.getString("result");
                            String msg = jsonObject.getString("msg");

                            Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();

                            if (result.equalsIgnoreCase("true")){
                                session.setValue(Constants.Key.user_name, name);
                                session.setValue(Constants.Key.user_about, bio);

                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                    }
                });
    }
}