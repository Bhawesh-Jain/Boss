package com.boss.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.adapter.TabLayoutUserProfileAdapter;
import com.boss.databinding.ActivityUserProfileBinding;
import com.boss.util.Session;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityUserProfileBinding binding;
    private final Activity activity = UserProfileActivity.this;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        init();
    }

    private void init() {
        getProfile();

        binding.icMoreOption.setOnClickListener(this);
        ArrayList<Integer> Integers = new ArrayList<>();
        Integers.add(R.drawable.ic_instagram_reel);
        Integers.add(R.drawable.ic_at);
        Integers.add(R.drawable.ic_info);
        Integers.add(R.drawable.ic_star);
        Integers.add(R.drawable.ic_stream_svgrepo);

        TabLayoutUserProfileAdapter tabLayoutAdapterSecond = new TabLayoutUserProfileAdapter(UserProfileActivity.this);
        binding.ViewPager.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.ViewPager, (tab, position) -> tab.setIcon(Integers.get(position))).attach();

    }

    private void getProfile() {

    }


    @Override
    public void onClick(View view) {
        if (view == binding.icMoreOption) dialogThreeDots();
    }

    private void dialogThreeDots() {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_three_dots);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue_curve_background);
        dialog.show();
    }
}