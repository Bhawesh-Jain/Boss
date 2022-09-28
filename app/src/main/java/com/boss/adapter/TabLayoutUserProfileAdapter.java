package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.fragment.UserProfilePostFragment;
import com.boss.view.fragment.UserProfileVideoFragment;

public class TabLayoutUserProfileAdapter extends FragmentStateAdapter {

    public TabLayoutUserProfileAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return new UserProfileVideoFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
