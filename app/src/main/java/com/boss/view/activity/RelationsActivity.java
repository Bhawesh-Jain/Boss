package com.boss.view.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.boss.adapter.FollowViewPager;
import com.boss.databinding.ActivityRelationsBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class RelationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRelationsBinding binding = ActivityRelationsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int type = 1;
        String user_id = "";
        if (getIntent() != null) {
            type = getIntent().getIntExtra("type", 1);
            user_id = getIntent().getStringExtra("user_id");
        }

        binding.viewPager.setAdapter(new FollowViewPager(this, user_id));

        String[] titles = new String[]{"Followers", "Following"};
        new TabLayoutMediator(binding.tabLay, binding.viewPager, ((tab, position) -> tab.setText(titles[position]))).attach();
        if (type == 0){
            binding.viewPager.setCurrentItem(1);
        }
    }
}