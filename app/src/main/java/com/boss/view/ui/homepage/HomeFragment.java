package com.boss.view.ui.homepage;

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

import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.HomeReelAdapter;
import com.boss.databinding.FragmentHomeBinding;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private Session session;

    private FragmentHomeBinding binding;
    private Activity activity;
    private ProgressDialog progressDialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = requireActivity();
        progressDialog = new ProgressDialog(activity);
        progressDialog.show();

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        session = new Session(getActivity());
        getReels();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getReels() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getReels(session.getUser_Id()).enqueue(new Callback<HomeReelModel>() {
            @Override
            public void onResponse(@NonNull Call<HomeReelModel> call, @NonNull Response<HomeReelModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                HomeReelAdapter adapter = new HomeReelAdapter(requireActivity(), response.body());
                                binding.videoViewPager.setAdapter(adapter);
                            } else Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomeReelModel> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }


}