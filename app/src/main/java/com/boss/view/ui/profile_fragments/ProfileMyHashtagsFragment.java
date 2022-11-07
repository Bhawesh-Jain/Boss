package com.boss.view.ui.profile_fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boss.databinding.FragmentHastagBinding;
import com.boss.util.Session;


public class ProfileMyHashtagsFragment extends Fragment{
    private FragmentHastagBinding binding;
    private Activity activity;
    private Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentHastagBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        return binding.getRoot();
    }
}