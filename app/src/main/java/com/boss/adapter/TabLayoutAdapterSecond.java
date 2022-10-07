package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.model.Response_Models.BannerResModel;
import com.boss.view.fragment.DiscoveryFragment;
import com.boss.view.fragment.TrendsFragment;

import java.util.ArrayList;

public class TabLayoutAdapterSecond extends FragmentStateAdapter {
    private final ArrayList<BannerResModel.Datum> discoveryList;
    private final ArrayList<BannerResModel.Datum> trendList;
    private final String path;

    public TabLayoutAdapterSecond(@NonNull FragmentActivity fragmentActivity,
                                  ArrayList<BannerResModel.Datum> discoveryList, ArrayList<BannerResModel.Datum> trendList, String path) {
        super(fragmentActivity);
        this.discoveryList = discoveryList;
        this.trendList = trendList;
        this.path = path;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new DiscoveryFragment(discoveryList, path);
        }
        return new TrendsFragment(trendList, path);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
