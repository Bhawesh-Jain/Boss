package com.boss.view.activity;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.FaqAdapter;
import com.boss.databinding.ActivityFaqBinding;
import com.boss.model.Response_Models.FaqModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {

    private ActivityFaqBinding binding;
    private final Activity activity = FaqActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backImg.setOnClickListener(view -> onBackPressed());

        getTermsCondition();
    }

    private void getTermsCondition() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getFaq().enqueue(new Callback<FaqModel>() {
            @Override
            public void onResponse(@NonNull Call<FaqModel> call, @NonNull Response<FaqModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
//                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().get(), Html.FROM_HTML_MODE_COMPACT));
                            FaqAdapter adapter = new FaqAdapter(response.body().getData(), activity);
                            binding.recyclerView.setAdapter(adapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                        } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FaqModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}
