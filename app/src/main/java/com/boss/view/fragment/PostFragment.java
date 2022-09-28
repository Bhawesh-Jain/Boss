package com.boss.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.databinding.FragmentPostBinding;

public class PostFragment extends Fragment implements View.OnClickListener{

    FragmentPostBinding binding;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentPostBinding.inflate(inflater, container, false);
       activity=requireActivity();
       binding.mcvPost.setOnClickListener(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==binding.mcvPost){
            try{
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}