package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.adapter.NotificationAdapter;
import com.boss.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private FragmentNotificationsBinding binding;
    Activity activity;
    ArrayList<String>strings=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        activity = requireActivity();
        init();

        return  binding.getRoot();
    }
    private void init(){
        strings.clear();
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");
        strings.add("1");

        binding.rvNotification.setLayoutManager(new LinearLayoutManager(activity));
        binding.rvNotification.setAdapter(new NotificationAdapter(activity,strings));
        binding.rvNotification.setHasFixedSize(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}