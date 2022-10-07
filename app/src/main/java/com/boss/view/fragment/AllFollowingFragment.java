package com.boss.view.fragment;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

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
import com.boss.adapter.FollowersListAdapter;
import com.boss.adapter.FollowingListAdapter;
import com.boss.databinding.FragmentAllFollowingBinding;
import com.boss.model.Response_Models.RelationUserModel;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllFollowingFragment extends Fragment {

    private FragmentAllFollowingBinding binding;
    private String user_id = "";
    private Session session;

    public AllFollowingFragment() {
    }

    public AllFollowingFragment(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAllFollowingBinding.inflate(inflater, container, false);

        session = new Session(getContext());

        getFollowing();

        return binding.getRoot();
    }

    private void getFollowing() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.getFollowingList(user_id).enqueue(new Callback<RelationUserModel>() {
            @Override
            public void onResponse(@NonNull Call<RelationUserModel> call, @NonNull Response<RelationUserModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setAdapter(new FollowingListAdapter(getContext(), response.body().getData()));
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        } else {
                            getActivity().onBackPressed();
                            Toast.makeText(getContext(), "No Followings!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RelationUserModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}