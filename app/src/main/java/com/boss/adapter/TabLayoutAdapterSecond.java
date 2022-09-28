package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.fragment.DiscoveryFragment;
import com.boss.view.fragment.TrendsFragment;

public class TabLayoutAdapterSecond extends FragmentStateAdapter {

    public TabLayoutAdapterSecond(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new DiscoveryFragment();
        }
        if (position == 1) {
            return new TrendsFragment();
        }
       /* if (position == 2) {
            return new MusicFragments();
        }*/

        return new DiscoveryFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
