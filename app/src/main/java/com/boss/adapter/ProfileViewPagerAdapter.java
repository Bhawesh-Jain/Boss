package com.boss.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.boss.view.ui.profile_fragments.ProfileMyDraftReelFragment;
import com.boss.view.ui.profile_fragments.ProfileMyHashtagsFragment;
import com.boss.view.ui.profile_fragments.ProfileMyLikedReelFragment;
import com.boss.view.ui.profile_fragments.ProfileMyLikeReelFragment;
import com.boss.view.fragment.VideoFragment;
import com.boss.view.ui.profile_fragments.ProfileMyViewReelFragment;

public class ProfileViewPagerAdapter extends FragmentStateAdapter {

    public ProfileViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new ProfileMyViewReelFragment();
        }
        if (position == 1) {
            return new ProfileMyLikedReelFragment();
        }
        if (position == 2) {
            return new ProfileMyHashtagsFragment();
        }
        if (position == 3) {
            return new ProfileMyLikeReelFragment();
        }
        if (position == 4) {
            return new ProfileMyDraftReelFragment();
        }
        return new VideoFragment();
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
