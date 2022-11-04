package com.boss.view.fragment;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.NotificationAdapter;
import com.boss.databinding.FragmentNotificationsBinding;
import com.boss.model.Response_Models.AdminNotiResModel;
import com.boss.util.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    private Activity activity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    private void init() {
        getAdminNotification();
    }

    private void getAdminNotification() {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getAdminNotification().enqueue(new Callback<AdminNotiResModel>() {
            @Override
            public void onResponse(@NonNull Call<AdminNotiResModel> call, @NonNull Response<AdminNotiResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.rvNotification.setLayoutManager(new LinearLayoutManager(activity));
                            binding.rvNotification.setAdapter(new NotificationAdapter(activity, response.body().getData(), response.body().getPath()));
                        } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AdminNotiResModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}