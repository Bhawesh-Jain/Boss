package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.UtilityTools.Utils;
import com.boss.view.activity.SearchActivity;
import com.boss.adapter.TabLayoutAdapterSecond;
import com.boss.databinding.FragmentSearchBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements View.OnClickListener{
    FragmentSearchBinding binding;
    Activity activity;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding= FragmentSearchBinding.inflate(inflater, container, false);
        activity=requireActivity();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<Integer> Integers = new ArrayList<>();
        Integers.add(R.drawable.ic_video);
        Integers.add(R.drawable.ic_explosive);
        //Integers.add(R.drawable.ic_music);

        title.add("Discovery");
        title.add("Trends");
      //  title.add("Music");

        TabLayoutAdapterSecond tabLayoutAdapterSecond=new TabLayoutAdapterSecond(getActivity());
        binding.vpLaunchId.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position)).setIcon(Integers.get(position));
        }).attach();

        binding.mcvSearch.setOnClickListener(this);


        return binding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view==binding.mcvSearch){
            Utils.I(activity, SearchActivity.class,null);
        }
    }
}