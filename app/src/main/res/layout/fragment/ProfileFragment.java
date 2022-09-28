package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.UtilityTools.Utils;
import com.boss.view.activity.UpdateProfile;
import com.boss.adapter.ProfileViewPagerAdapter;
import com.boss.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    FragmentProfileBinding binding;
    Activity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return binding.getRoot();
    }

    private void init() {
        binding.editProfileTv.setOnClickListener(this);
        binding.myprofileImg.setOnClickListener(this);
        ProfileViewPagerAdapter profileViewPagerAdapter = new ProfileViewPagerAdapter(getActivity());
        binding.ViewPager.setAdapter(profileViewPagerAdapter);

        ArrayList<Integer> integers=new ArrayList<>();
        integers.add(R.drawable.ic_instagram_reel);
        integers.add(R.drawable.ic_at);
        integers.add(R.drawable.ic_at);
        integers.add(R.drawable.ic_heart);
        integers.add(R.drawable.ic_file);
        new TabLayoutMediator(binding.topTabLayout, binding.ViewPager, ((tab, position) -> tab.setIcon(integers.get(position)))).attach();

    }

    @Override
    public void onClick(View view) {
        if(view==binding.editProfileTv){
            Utils.I(activity, UpdateProfile.class,null);
        }else   if(view==binding.myprofileImg){
            Utils.I(activity, UpdateProfile.class,null);
        }
    }
}