package com.boss.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.boss.adapter.TabLayoutSearchAdapter;
import com.boss.databinding.ActivitySearchBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    ActivitySearchBinding binding;
    Activity activity = SearchActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        String[] title = new String[]{"All", "Videos", "Users", "Photos", "Hashtags"};
        TabLayoutSearchAdapter tabLayoutAdapterSecond = new TabLayoutSearchAdapter(this);
        binding.vpLaunchId.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title[position]);
        }).attach();
    }

    @Override
    public void onClick(View view) {

    }
}