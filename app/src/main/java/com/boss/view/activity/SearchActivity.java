package com.boss.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.boss.adapter.TabLayoutSearchAdapter;
import com.boss.databinding.ActivitySearchBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    ActivitySearchBinding binding;
    Activity activity=SearchActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }
    private void init(){
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<Integer> Integers = new ArrayList<>();
      //  Integers.add(R.drawable.ic_video);
      //  Integers.add(R.drawable.ic_explosive);
        //Integers.add(R.drawable.ic_music);

        title.add("All");
        title.add("Videos");
        title.add("Users");
        title.add("Photos");
        title.add("Hashtags");
        title.add("Hashtags");

        TabLayoutSearchAdapter tabLayoutAdapterSecond=new TabLayoutSearchAdapter(this);
        binding.vpLaunchId.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position));
        }).attach();
    }

    @Override
    public void onClick(View view) {

    }
}