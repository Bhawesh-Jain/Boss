package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.fragment.DraftFragment;
import com.boss.view.fragment.HastagFragment;
import com.boss.view.fragment.LikeFragment;
import com.boss.view.fragment.PostFragment;
import com.boss.view.fragment.VideoFragment;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new VideoFragment();
        }
        if (position == 1) {
            return new PostFragment();
        }
        if (position == 2) {
            return new HastagFragment();
        }
        if (position == 3) {
            return new LikeFragment();
        }
        if (position == 4) {
            return new DraftFragment();
        }
        return new VideoFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
