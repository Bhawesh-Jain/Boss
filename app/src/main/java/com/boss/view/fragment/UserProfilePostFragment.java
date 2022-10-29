package com.boss.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.adapter.PostAdapter;
import com.boss.databinding.FragmentUserProfilePostBinding;
import com.boss.databinding.FragmentVideoBinding;

import java.util.ArrayList;


public class UserProfilePostFragment extends Fragment {
    FragmentUserProfilePostBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserProfilePostBinding.inflate(inflater, container, false);
        activity = requireActivity();

        list.clear();
        list.add("1");
        list.add("1");


        GridLayoutManager gridLayout = new GridLayoutManager(activity, 2);
        binding.rvVideo.setLayoutManager(gridLayout);
        binding.rvVideo.setHasFixedSize(true);
        binding.rvVideo.setAdapter(new PostAdapter(activity, list));
        return binding.getRoot();
    }


}