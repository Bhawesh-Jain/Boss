package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.adapter.PostAdapter;
import com.boss.databinding.FragmentPhotosBinding;
import com.boss.databinding.FragmentVideoBinding;

import java.util.ArrayList;


public class PhotosFragment extends Fragment {
    FragmentPhotosBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPhotosBinding.inflate(inflater, container, false);
        activity = requireActivity();


        GridLayoutManager gridLayout = new GridLayoutManager(activity, 4);
        binding.rvVideo.setLayoutManager(gridLayout);
        binding.rvVideo.setHasFixedSize(true);
        binding.rvVideo.setAdapter(new PostAdapter(activity, list));
        return binding.getRoot();
    }
}