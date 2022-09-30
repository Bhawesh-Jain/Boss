package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.model.Response_Models.HomeReelModel;
import com.boss.view.homepage.PhotoReelFragment;
import com.boss.view.homepage.TextReelFragment;
import com.boss.view.homepage.VideoReelFragment;

public class HomeReelAdapter extends FragmentStateAdapter {
    private final HomeReelModel homeReelModel;

    public HomeReelAdapter(@NonNull FragmentActivity fragmentActivity, HomeReelModel homeReelModel) {
        super(fragmentActivity);
        this.homeReelModel = homeReelModel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        HomeReelModel.Datum currentModel = homeReelModel.getData().get(position);
        currentModel.setReelPath(homeReelModel.getReelPath());
        currentModel.setUserPath(homeReelModel.getUserPath());

        if (currentModel.getType().equalsIgnoreCase("video"))
            return new VideoReelFragment(currentModel);
        else if (currentModel.getType().equalsIgnoreCase("text"))
            return new TextReelFragment(currentModel);
        else
            return new PhotoReelFragment(currentModel);
    }

    @Override
    public int getItemCount() {
        return homeReelModel.getData().size();
    }
}
