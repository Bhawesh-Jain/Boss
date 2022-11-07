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
import com.boss.adapter.MyViewReelAdapter;
import com.boss.databinding.FragmentProfileMyViewReelBinding;
import com.boss.model.Response_Models.MyViewsReelModel;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileMyViewReelFragment extends Fragment {

    private FragmentProfileMyViewReelBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileMyViewReelBinding.inflate(inflater, container, false);
        activity = getActivity();
        session = new Session(activity);

        getMyViewReels();

        return binding.getRoot();
    }

    private void getMyViewReels() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getMyViewReels(session.getUser_Id()).enqueue(new Callback<MyViewsReelModel>() {
            @Override
            public void onResponse(@NonNull Call<MyViewsReelModel> call, @NonNull Response<MyViewsReelModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() != 0) {
                                binding.recyclerView.setAdapter(new MyViewReelAdapter(activity, response.body().getData(), response.body().getPath()));
                                binding.recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
                                binding.llNoVideo.setVisibility(View.GONE);
                            }
                        } else binding.llNoVideo.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MyViewsReelModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}