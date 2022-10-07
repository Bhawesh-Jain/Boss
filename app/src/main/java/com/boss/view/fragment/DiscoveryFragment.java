package com.boss.view.fragment;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.BannerAdapter;
import com.boss.adapter.PopularCreatorAdapter;
import com.boss.adapter.TabLayoutAdapterSecond;
import com.boss.databinding.FragmentDiscoveryBinding;
import com.boss.model.Response_Models.BannerResModel;
import com.boss.model.Response_Models.PopularCreatorResModel;
import com.boss.util.Session;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DiscoveryFragment extends Fragment {
    private FragmentDiscoveryBinding binding;
    private Activity activity;
    private ArrayList<BannerResModel.Datum> list = new ArrayList<>();
    private final String path;
    private Session session;

    public DiscoveryFragment(ArrayList<BannerResModel.Datum> list, String path) {
        this.list = list;
        this.path = path;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        binding.bannerRecycler.setAdapter(new BannerAdapter(getContext(), list, path));
        binding.bannerRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        getPopularCreator();
        return binding.getRoot();
    }

    private void getPopularCreator() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.getPopularCreator(session.getUser_Id()).enqueue(new Callback<PopularCreatorResModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<PopularCreatorResModel> call, @NonNull Response<PopularCreatorResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                binding.popularCreatorRecycler.setAdapter(new PopularCreatorAdapter(response.body(), getContext()));
                                binding.popularCreatorRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PopularCreatorResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}