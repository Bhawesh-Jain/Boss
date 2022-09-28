package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.adapter.NotificationAdapter;
import com.boss.adapter.PostAdapter;
import com.boss.databinding.FragmentAllBinding;
import com.boss.databinding.FragmentPhotosBinding;

import java.util.ArrayList;


public class AllFragment extends Fragment {

    FragmentAllBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String>strings=new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllBinding.inflate(inflater, container, false);
        activity = requireActivity();
//        binding.mcvPost.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");



        strings.clear();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");


        binding.rvNotification.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,true));
        binding.rvNotification.setAdapter(new NotificationAdapter(activity,strings));
        binding.rvNotification.setHasFixedSize(true);

        GridLayoutManager gridLayout = new GridLayoutManager(activity, 2);
        binding.rvVideo.setLayoutManager(gridLayout);
        binding.rvVideo.setHasFixedSize(true);
        binding.rvVideo.setAdapter(new PostAdapter(activity, list));




        return binding.getRoot();
    }
}