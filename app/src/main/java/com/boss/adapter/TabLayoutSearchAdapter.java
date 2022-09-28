package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.fragment.AllFragment;
import com.boss.view.fragment.HashtagsFragment;
import com.boss.view.fragment.PhotosFragment;
import com.boss.view.fragment.UsersFragment;
import com.boss.view.fragment.VideoFragment;

public class TabLayoutSearchAdapter extends FragmentStateAdapter {

    public TabLayoutSearchAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new AllFragment();
        }
        if (position == 1) {
            return new VideoFragment();
        }
        if (position == 2) {
            return new UsersFragment();
        }
        if (position == 3) {
            return new PhotosFragment();
        }
        if (position == 4) {
            return new HashtagsFragment();
        }

        return new AllFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
