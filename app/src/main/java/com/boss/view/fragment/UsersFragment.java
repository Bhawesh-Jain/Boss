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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.AllUserAdapter;
import com.boss.databinding.FragmentUsersBinding;
import com.boss.model.Response_Models.AllUsersModel;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragment extends Fragment {

    private final String TAG = "UsersFragment";
    private FragmentUsersBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        getUsers();

        return binding.getRoot();
    }

    private void getUsers() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getUsers(session.getUser_Id()).enqueue(new Callback<AllUsersModel>() {
            @Override
            public void onResponse(@NonNull Call<AllUsersModel> call, @NonNull Response<AllUsersModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {

                                AllUserAdapter adapter = new AllUserAdapter(activity, response.body().getData(), response.body().getPath());
                                binding.recyclerView.setAdapter(adapter);
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));

                            } else
                                Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AllUsersModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

}