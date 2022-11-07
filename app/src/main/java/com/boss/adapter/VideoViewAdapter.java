package com.boss.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.UtilityTools.Utils;
import com.boss.view.activity.UserProfileActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;
import com.jarvanmo.exoplayerview.ui.ExoVideoView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class VideoViewAdapter extends RecyclerView.Adapter<VideoViewAdapter.MyViewHolder> implements HomeInterface {

    Context context;
    ArrayList<String> video_url;
    ExoVideoView videoView;

    public VideoViewAdapter(Context context, ArrayList<String> video_url) {
        this.context = context;
        this.video_url = video_url;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.tiktok_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.progress_bar.setVisibility(View.GONE);
        SimpleMediaSource mediaSource = new SimpleMediaSource(video_url.get(position));

        videoView.hideController();
        videoView.setControllerAutoShow(false);
        videoView.setUseController(false);
        videoView.changeWidgetVisibility(R.id.exo_player_controller_back, View.INVISIBLE);

        holder.userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.I(context, UserProfileActivity.class, null);
            }
        });


        holder.report_abuse_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetDialog bottomSheet = new BottomSheetDialog(context, R.style.BottomSheetDialog);
                bottomSheet.setContentView(R.layout.report_abuse_lay);
                bottomSheet.setCancelable(true);
                bottomSheet.setCanceledOnTouchOutside(true);
                bottomSheet.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                bottomSheet.show();
            }
        });


        int adapter_pos = position;


//        HomeFragment.videoview_pager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//
//
//                if (adapter_pos == position) {
//                    if (videoView != null) {
//                        videoView.stop();
//                    }
//
//                    videoView.play(mediaSource);
//                    videoView.resume();
//                } else {
//                    videoView.pause();
//                    videoView.stop();
//                }
//
//                Log.e(" ", "onPageScrolled: " + position);
//
//
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return video_url.size();
    }

    @Override
    public void onClick(String value) {

        videoView.pause();

        videoView.stop();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progress_bar;
        ImageView report_abuse_img, userImage;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.fullscreenVideoView);
            progress_bar = itemView.findViewById(R.id.progress_bar);
            report_abuse_img = itemView.findViewById(R.id.report_abuse_img);
            userImage = itemView.findViewById(R.id.user_images);
        }
    }


}
