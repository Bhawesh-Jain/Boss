package com.boss.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.adapter.BannerAdapter;
import com.boss.adapter.TabLayoutAdapterSecond;
import com.boss.databinding.FragmentSearchBinding;
import com.boss.model.HomeBannerOfferModel;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;


public class SearchFragment extends Fragment {
    FragmentSearchBinding binding;
    Activity activity;
    ArrayList<HomeBannerOfferModel> banners1 = new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    CountDownTimer countDownTimer = null;
    BannerAdapter bannerAdapter;
    private final Handler sliderHandler = new Handler();


   /* private final Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.vpBanner.setCurrentItem(binding.vpBanner.getCurrentItem() + 1);
            if (binding.vpBanner.getCurrentItem() == banners1.size() - 1) {
                countDownTimer = new CountDownTimer(3000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        binding.vpBanner.setCurrentItem(0);
                    }
                }.start();
            }
        }
    };

*/
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        activity=requireActivity();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<Integer> Integers = new ArrayList<>();
        Integers.add(R.drawable.ic_video);
        Integers.add(R.drawable.ic_explosive);
        Integers.add(R.drawable.ic_music);

        title.add("Discovery");
        title.add("Trends");
        title.add("Music");

        TabLayoutAdapterSecond tabLayoutAdapterSecond=new TabLayoutAdapterSecond(getActivity());
        binding.vpLaunchId.setAdapter(tabLayoutAdapterSecond);

        new TabLayoutMediator(binding.tabLayout, binding.vpLaunchId, (tab, position) -> {
            tab.setText(title.get(position)).setIcon(Integers.get(position));
        }).attach();







/*
        banners1.clear();
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        banners1.add(new HomeBannerOfferModel(R.drawable.ganeshji));
        bannerAdapter = new BannerAdapter(activity, banners1);
        binding.vpBanner.setAdapter(bannerAdapter);
        binding.dot2.setViewPager2(binding.vpBanner);
        binding.vpBanner.setClipToPadding(false);
        binding.vpBanner.setClipChildren(false);
        binding.vpBanner.setOffscreenPageLimit(3);
        binding.vpBanner.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        binding.vpBanner.setPageTransformer(compositePageTransformer);
        binding.vpBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }


            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 3000); // slide duration 3 seconds

            }
        });*/

        return binding.getRoot();
    }
}