package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.databinding.FragmentTrendsBinding;

import java.util.ArrayList;


public class TrendsFragment extends Fragment {
    FragmentTrendsBinding binding;
    Activity activity;
    ArrayList<String> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentTrendsBinding.inflate(inflater, container, false);
        activity=requireActivity();


/*        list.clear();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");


        binding.rvBanner.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true));
        binding.rvBanner.setHasFixedSize(true);
        binding.rvBanner.setAdapter(new BannerAdapter(activity,list));*/

        return binding.getRoot();    }
}