package com.boss.view.activity;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.PrivacyPolicyAdapter;
import com.boss.databinding.ActivityPrivacyPolicyBinding;
import com.boss.model.Response_Models.AboutUsModel;
import com.boss.model.Response_Models.PrivacyPolModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity {

    private ActivityPrivacyPolicyBinding binding;
    private final Activity activity = PrivacyPolicyActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backImg.setOnClickListener(view -> onBackPressed());

        getPrivacyPolicy();
    }

    private void getPrivacyPolicy() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getPrivacyPolicy().enqueue(new Callback<PrivacyPolModel>() {
            @Override
            public void onResponse(@NonNull Call<PrivacyPolModel> call, @NonNull Response<PrivacyPolModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
//                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().get(), Html.FROM_HTML_MODE_COMPACT));
                            PrivacyPolicyAdapter adapter = new PrivacyPolicyAdapter(response.body().getData(), activity);
                            binding.recyclerView.setAdapter(adapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                        } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PrivacyPolModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}