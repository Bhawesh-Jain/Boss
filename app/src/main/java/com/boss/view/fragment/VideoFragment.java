package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.SearchPhotoReelAdapter;
import com.boss.databinding.FragmentVideoBinding;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.Session;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class VideoFragment extends Fragment {
    private final String TAG = "SearchVideoFragment";
    private FragmentVideoBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        getReels();

        return binding.getRoot();
    }

    private void getReels() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getReels(session.getUser_Id()).enqueue(new Callback<HomeReelModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeReelModel> call, @NonNull Response<HomeReelModel> response) {
                binding.progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                List<HomeReelModel.Datum> filteredList = new ArrayList<>();
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    if (response.body().getData().get(i).getType().equalsIgnoreCase("video"))
                                        filteredList.add(response.body().getData().get(i));
                                }
                                if (filteredList.size() == 0)
                                    binding.llBody.setVisibility(View.VISIBLE);
                                else {
                                    binding.llBody.setVisibility(View.GONE);
                                    GridLayoutManager gridLayout = new GridLayoutManager(activity, 3);
                                    binding.rvVideo.setLayoutManager(gridLayout);
                                    binding.rvVideo.setAdapter(new SearchPhotoReelAdapter(activity, filteredList, response.body().getReelPath()));
                                }
                            } else {
                                Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeReelModel> call, @NonNull Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}