package com.boss.view.activity;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ActivityAboutUsBinding;
import com.boss.model.Response_Models.AboutUsModel;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.util.Constants;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {

    private ActivityAboutUsBinding binding;
    private final Activity activity = AboutUsActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backImg.setOnClickListener(view -> onBackPressed());
        getAboutUs();
    }

    private void getAboutUs() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getAboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(@NonNull Call<AboutUsModel> call, @NonNull Response<AboutUsModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AboutUsModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }
}