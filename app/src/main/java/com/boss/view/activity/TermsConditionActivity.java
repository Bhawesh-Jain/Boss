package com.boss.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.TermsConditionAdapter;
import com.boss.databinding.ActivityTermsConditionBinding;
import com.boss.model.TermsConditionModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsConditionActivity extends AppCompatActivity {

    private ActivityTermsConditionBinding binding;
    private final Activity activity = TermsConditionActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getTermsCondition();
    }

    private void getTermsCondition() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getTermsCondition().enqueue(new Callback<TermsConditionModel>() {
            @Override
            public void onResponse(@NonNull Call<TermsConditionModel> call, @NonNull Response<TermsConditionModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
//                            binding.tvAboutUs.setText(Html.fromHtml(response.body().getData().get(), Html.FROM_HTML_MODE_COMPACT));
                            TermsConditionAdapter adapter = new TermsConditionAdapter(response.body().getData(), activity);
                            binding.recyclerView.setAdapter(adapter);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                        } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TermsConditionModel> call, @NonNull Throwable t) {
                Toast.makeText(activity, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}