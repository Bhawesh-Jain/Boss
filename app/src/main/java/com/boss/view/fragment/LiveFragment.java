package com.boss.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.R;


public class LiveFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            getActivity().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return inflater.inflate(R.layout.fragment_live, container, false);
    }
}