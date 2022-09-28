package com.boss.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivityLoginBinding;
import com.boss.model.Response_Models.LoginModel;
import com.boss.util.Constants;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final Activity activity = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.white));

        binding.tvLogin.setOnClickListener(view -> {
            String mobile = binding.edtMobile.getText().toString();
            if (mobile.length() < 10){
                binding.edtMobile.setError("Enter a valid Mobile Number");
            }else {
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

    private void login(String mobile) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.login(mobile).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null){
                        if (response.body().getResult().equalsIgnoreCase("true")){
                            startActivity(new Intent(activity, OtpVerificationActivity.class)
                                    .putExtra(Constants.Key.user_id, String.valueOf(response.body().getData().getId()))
                                    .putExtra(Constants.Key.mobile, mobile)
                                    .putExtra(Constants.Key.otp, String.valueOf(response.body().getData().getOtp())));
                            finish();
                        } else Toast.makeText(activity, ""+response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(activity, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}