package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SearchTabAdapter extends FragmentStateAdapter {

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    public SearchTabAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
