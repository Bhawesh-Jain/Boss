package com.boss.view.fragment;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.TabLayoutAdapterSecond;
import com.boss.databinding.FragmentSearchBinding;
import com.boss.model.Response_Models.BannerResModel;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.view.activity.SearchActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment implements View.OnClickListener {
    private FragmentSearchBinding binding;
    private Activity activity;
    private ArrayList<BannerResModel.Datum> discoverList = new ArrayList<>();
    private ArrayList<BannerResModel.Datum> trendList = new ArrayList<>();
    private TabLayoutAdapterSecond tabLayoutAdapterSecond;
    private String path = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        activity = requireActivity();

        binding.mcvSearch.setOnClickListener(this);

        getBanner();

        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view == binding.mcvSearch) {
            startActivity(new Intent(activity, SearchActivity.class));
        }
    }

    private void getBanner() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.getBanner().enqueue(new Callback<BannerResModel>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<BannerResModel> call, @NonNull Response<BannerResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                for (int i = 0; i < response.body().getData().size(); i++){
                                    if (response.body().getData().get(i).getType().equalsIgnoreCase("discovery"))
                                        discoverList.add(response.body().getData().get(i));
                                    else
                                        trendList.add(response.body().getData().get(i));
                                }
                                path = response.body().getPath();

                                ArrayList<String> title = new ArrayList<>();
                                ArrayList<Integer> icons = new ArrayList<>();
                                icons.add(R.drawable.ic_video);
                                icons.add(R.drawable.ic_explosive);

                                title.add("Discovery");
                                title.add("Trends");

                                tabLayoutAdapterSecond = new TabLayoutAdapterSecond(getActivity(), discoverList, trendList, path);
                                binding.vpLaunchId.setAdapter(tabLayoutAdapterSecond);

                                new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> tab.setText(title.get(position)).setIcon(icons.get(position))).attach();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<BannerResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}