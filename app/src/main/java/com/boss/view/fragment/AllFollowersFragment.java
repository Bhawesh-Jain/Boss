package com.boss.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.FollowersListAdapter;
import com.boss.databinding.FragmentAllFollowersBinding;
import com.boss.model.Response_Models.RelationUserModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllFollowersFragment extends Fragment {

    private FragmentAllFollowersBinding binding;
    private String user_id = "";

    public AllFollowersFragment() {
    }

    public AllFollowersFragment(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllFollowersBinding.inflate(inflater, container, false);

        getFollowers();

        return binding.getRoot();
    }

    private void getFollowers() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.getFollowersList(user_id).enqueue(new Callback<RelationUserModel>() {
            @Override
            public void onResponse(@NonNull Call<RelationUserModel> call, @NonNull Response<RelationUserModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setAdapter(new FollowersListAdapter(getContext(), response.body().getData()));
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        } else {
                            getActivity().onBackPressed();
                            Toast.makeText(getContext(), "No Followers!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RelationUserModel> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}