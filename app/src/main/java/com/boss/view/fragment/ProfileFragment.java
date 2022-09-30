package com.boss.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.UtilityTools.Utils;
import com.boss.adapter.ProfileViewPagerAdapter;
import com.boss.databinding.FragmentProfileBinding;
import com.boss.model.Response_Models.ProfileModel;
import com.boss.util.Constants;
import com.boss.util.Session;
import com.boss.view.activity.RelationsActivity;
import com.boss.view.activity.SettingActivity;
import com.boss.view.activity.UpdateProfile;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements View.OnClickListener {
    private FragmentProfileBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);
        init();

        return binding.getRoot();
    }

    private void init() {
        getProfile();

        binding.tvUserName.setText(session.getValue(Constants.Key.user_name));
        binding.tvCompanyName.setText(session.getValue(Constants.Key.user_company));
        binding.tvAboutBusiness.setText(session.getValue(Constants.Key.user_about));

        String profileImg = session.getValue(Constants.Key.user_img);
        if (profileImg.length() != 0)
            Glide.with(activity)
                    .load(profileImg)
                    .error(R.drawable.user)
                    .placeholder(R.drawable.user)
                    .into(binding.icProfileImg);

        binding.editProfileTv.setOnClickListener(this);
        binding.icProfileImg.setOnClickListener(this);
        binding.icSettings.setOnClickListener(this);
        binding.followersLay.setOnClickListener(this);
        binding.followingLay.setOnClickListener(this);

        ProfileViewPagerAdapter profileViewPagerAdapter = new ProfileViewPagerAdapter(getActivity());
        binding.ViewPager.setAdapter(profileViewPagerAdapter);
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(R.drawable.ic_instagram_reel);
        integers.add(R.drawable.ic_at);
        integers.add(R.drawable.ic_help);
        integers.add(R.drawable.ic_heart);
        integers.add(R.drawable.ic_file);

        new TabLayoutMediator(binding.topTabLayout, binding.ViewPager, ((tab, position) -> tab.setIcon(integers.get(position)))).attach();
    }

    private void getProfile() {
        ApiService apiService = RetrofitClient.getClient(activity);

        apiService.getProfile(session.getUser_Id(), session.getUser_Id()).enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<ProfileModel> call, @NonNull Response<ProfileModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            ProfileModel.Data data = response.body().getData();

                            session.setValue(Constants.Key.user_name, data.getName());
                            session.setValue(Constants.Key.user_company, data.getCompany());
                            session.setValue(Constants.Key.user_about, data.getAbout());

                            binding.tvUserName.setText(data.getName());
                            binding.tvCompanyName.setText(data.getCompany());
                            binding.tvAboutBusiness.setText(data.getAbout());
                            binding.tvFollowers.setText(String.valueOf(data.getTotalFollower()));
                            binding.tvFollowing.setText(String.valueOf(data.getTotalFollowing()));

                            if (data.getImage().length() != 0) {
                                session.setValue(Constants.Key.user_img, response.body().getPath() + data.getImage());
                                Glide.with(activity)
                                        .load(response.body().getPath() + data.getImage())
                                        .error(R.drawable.user)
                                        .placeholder(R.drawable.user)
                                        .into(binding.icProfileImg);
                            }
                        } else
                            Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProfileModel> call, @NonNull Throwable t) {
                Toast.makeText(activity, "" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == binding.editProfileTv) Utils.I(activity, UpdateProfile.class, null);
        else if (view == binding.icProfileImg) Utils.I(activity, UpdateProfile.class, null);
        else if (view == binding.icSettings) Utils.I(activity, SettingActivity.class, null);
        else if (view == binding.followersLay) startActivity(new Intent(activity, RelationsActivity.class).putExtra("type", 1).putExtra("user_id", session.getUser_Id()));
        else if (view == binding.followingLay) startActivity(new Intent(activity, RelationsActivity.class).putExtra("type", 0).putExtra("user_id", session.getUser_Id()));

    }
}