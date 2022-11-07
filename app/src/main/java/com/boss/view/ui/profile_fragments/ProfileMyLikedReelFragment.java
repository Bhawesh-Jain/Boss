package com.boss.view.ui.profile_fragments;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.MyLikedReelAdapter;
import com.boss.databinding.FragmentPostBinding;
import com.boss.model.Response_Models.MyLikeReelModel;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMyLikedReelFragment extends Fragment {

    private FragmentPostBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        getMyLikedReels();

        return binding.getRoot();
    }

    private void getMyLikedReels() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getMyLikeReels(session.getUser_Id()).enqueue(new Callback<MyLikeReelModel>() {
            @Override
            public void onResponse(@NonNull Call<MyLikeReelModel> call, @NonNull Response<MyLikeReelModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() != 0) {
                                binding.recyclerView.setAdapter(new MyLikedReelAdapter(activity, response.body().getData(), response.body().getPath()));
                                binding.recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
                                binding.llNoVideo.setVisibility(View.GONE);
                            }
                        } else binding.llNoVideo.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyLikeReelModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }
}