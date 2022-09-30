package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.fragment.AllFollowersFragment;
import com.boss.view.fragment.AllFollowingFragment;

public class FollowViewPager extends FragmentStateAdapter {

    private final String user_id;
    public FollowViewPager(@NonNull FragmentActivity fragmentActivity, String user_id) {
        super(fragmentActivity);
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new AllFollowersFragment(user_id);
        else return new AllFollowingFragment(user_id);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
