package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.adapter.BannerAdapter;
import com.boss.databinding.FragmentTrendsBinding;
import com.boss.model.Response_Models.BannerResModel;

import java.util.ArrayList;


public class TrendsFragment extends Fragment {
    private FragmentTrendsBinding binding;
    private Activity activity;
    private ArrayList<BannerResModel.Datum> list=new ArrayList<>();
    private final String path;

    public TrendsFragment(ArrayList<BannerResModel.Datum> list, String path) {
        this.list = list;
        this.path = path;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentTrendsBinding.inflate(inflater, container, false);
        activity=requireActivity();

        binding.bannerRecycler.setAdapter(new BannerAdapter(getContext(), list, path));
        binding.bannerRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        return binding.getRoot();
    }
}