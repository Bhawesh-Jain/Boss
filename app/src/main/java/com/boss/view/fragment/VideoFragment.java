package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.adapter.PostAdapter;
import com.boss.databinding.FragmentVideoBinding;

import java.util.ArrayList;


public class VideoFragment extends Fragment implements View.OnClickListener {
    FragmentVideoBinding binding;
    Activity activity;
    ArrayList<String> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVideoBinding.inflate(inflater, container, false);
        activity = requireActivity();
//        binding.mcvPost.setOnClickListener(this);

        list.clear();
        list.add("1");
        list.add("1");


        GridLayoutManager gridLayout = new GridLayoutManager(activity, 2);
        binding.rvVideo.setLayoutManager(gridLayout);
        binding.rvVideo.setHasFixedSize(true);
        binding.rvVideo.setAdapter(new PostAdapter(activity, list));
        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
      /*  if(view==binding.mcvPost){
            try{
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                activity.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

        if (view == binding.rvVideo) {

        }
    }
}