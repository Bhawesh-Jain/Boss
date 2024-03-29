package com.boss.view.ui.homepage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.FragmentVideoReelBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.Session;
import com.boss.view.activity.ReelCommentActivity;
import com.boss.view.activity.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.jarvanmo.exoplayerview.media.SimpleMediaSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoReelFragment extends Fragment {

    private static SimpleMediaSource mediaSource;
    private final HomeReelModel.Datum model;
    private final String TAG = VideoReelFragment.class.getSimpleName();
    private FragmentVideoReelBinding binding;
    private boolean isPlaying = false;
    private Session session;
    private boolean isLiked;
    private int likeCount;

    public VideoReelFragment(HomeReelModel.Datum model) {
        this.model = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentVideoReelBinding.inflate(inflater, container, false);


        session = new Session(getContext());
        binding.reportAbuseImg.setOnClickListener(view -> dialogThreeDots());

        binding.tvUserName.setText(model.getName());
        binding.tvCommentCount.setText(String.valueOf(model.getCommentCount()));
        binding.tvLikeCount.setText(String.valueOf(model.getLikeCount()));
        binding.tvDesc.setText(model.getDescription());
        String text = model.getTotal_views() + " Views";
        binding.tvViews.setText(text);

        if (model != null)
            if (model.getUserImage() != null && !model.getUserImage().equalsIgnoreCase(""))
                if (getContext() != null)
                    Glide.with(getContext())
                            .load(model.getUserPath() + model.getUserImage())
                            .error(R.drawable.ic_user)
                            .placeholder(R.drawable.ic_user)
                            .into(binding.icUserImages)
                            .onLoadFailed(AppCompatResources.getDrawable(getContext(), R.drawable.ic_user));
        binding.tvUserName.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", model.getUserId())));
        binding.icUserImages.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", model.getUserId())));
        binding.cardFollow.setOnClickListener(view -> followUnfollow(model.getUserId()));
        binding.commentLay.setOnClickListener(view -> startActivity(new Intent(getContext(), ReelCommentActivity.class).putExtra("reel_id", model.getId())));

        binding.shareLay.setOnClickListener(view -> shareReel());

        isLiked = model.getiLiked() != 0;
        likeCount = model.getLikeCount();
        binding.likeLay.setOnClickListener(view -> {
            likeReel();
            if (!isLiked) {
                binding.likeImg.setImageResource(R.drawable.ic_likes_selected);
                binding.tvLikeCount.setText(String.valueOf((likeCount) + 1));
                isLiked = true;
                likeCount++;
            } else {
                binding.likeImg.setImageResource(R.drawable.ic_likes);
                binding.tvLikeCount.setText(String.valueOf((likeCount) - 1));
                isLiked = false;
                likeCount--;
            }
        });
        if (model.getiLiked() == 1) binding.likeImg.setImageResource(R.drawable.ic_likes_selected);
        else binding.likeImg.setImageResource(R.drawable.ic_likes);


        mediaSource = new SimpleMediaSource(model.getReelPath() + model.getFile());
        Log.e(TAG, "onBindViewHolder: Url" + model.getReelPath() + model.getFile());
        binding.videoView.hideController();
        binding.videoView.setControllerAutoShow(false);
        binding.videoView.setUseController(false);
        binding.videoView.changeWidgetVisibility(R.id.exo_player_controller_back, View.GONE);
        binding.videoView.play(mediaSource);
        isPlaying = true;

        binding.parent.setOnClickListener(view -> {
            if (view != null) {
                if (isPlaying) {
                    binding.videoView.pause();
                    isPlaying = false;
                } else {
                    isPlaying = true;
                    binding.videoView.resume();
                }
            }
        });
        viewReel();
        return binding.getRoot();
    }

    private void viewReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.viewReel(session.getUser_Id(), model.getId()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getMsg().equalsIgnoreCase("view successfuly")) {
                                String text = model.getTotal_views() + 1 + " Views";
                                binding.tvViews.setText(text);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }


    private void shareReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.shareReel(session.getUser_Id(), model.getId()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaSource != null) {
            isPlaying = true;
            binding.videoView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isPlaying = false;
        binding.videoView.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPlaying = false;
        binding.videoView.releasePlayer();
        mediaSource = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaSource != null) {
            isPlaying = false;
            binding.videoView.releasePlayer();
            mediaSource = null;
        }
    }

    private void likeReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.likeReel(session.getUser_Id(), model.getId()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getMsg().equalsIgnoreCase("Liked")) {
                                    model.setiLiked(1);
                                    model.setLikeCount(model.getLikeCount() + 1);
                                } else {
                                    model.setiLiked(0);
                                    model.setLikeCount(model.getLikeCount() - 1);
                                }
                            } else
                                Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());

                binding.tvLikeCount.setText(String.valueOf(model.getLikeCount()));
                if (model.getiLiked() == 1) {
                    binding.likeImg.setImageResource(R.drawable.ic_likes_selected);
                } else {
                    binding.likeImg.setImageResource(R.drawable.ic_likes);
                }
                binding.tvLikeCount.setText(binding.tvLikeCount.getText().toString());
                model.setLikeCount(Integer.parseInt(binding.tvLikeCount.getText().toString()));
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void dialogThreeDots() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_three_dots);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue_curve_background);
        dialog.show();
    }

    private void followUnfollow(String toUserId) {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.follow_Unfollow(session.getUser_Id(), toUserId).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getMsg().equalsIgnoreCase("Followed")) {
                                    binding.tvFollow.setText(R.string.following);
                                } else binding.tvFollow.setText(R.string.follow);
                            } else
                                Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }
}