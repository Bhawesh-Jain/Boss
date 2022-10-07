package com.boss.view.homepage;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.FragmentPhotoReelBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.Session;
import com.boss.view.activity.ReelCommentActivity;
import com.boss.view.activity.UserProfileActivity;
import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotoReelFragment extends Fragment {

    private final HomeReelModel.Datum reelModel;
    private FragmentPhotoReelBinding binding;
    private Session session;
    private boolean isLiked;
    private int likeCount;

    public PhotoReelFragment(HomeReelModel.Datum model) {
        this.reelModel = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhotoReelBinding.inflate(inflater, container, false);
        session = new Session(getContext());
        binding.reportAbuseImg.setOnClickListener(view -> dialogThreeDots());
        binding.tvUserName.setText(reelModel.getName());
        binding.tvCommentCount.setText(String.valueOf(reelModel.getCommentCount()));
        binding.tvLikeCount.setText(String.valueOf(reelModel.getLikeCount()));
        binding.tvDesc.setText(reelModel.getDescription());
        String text = reelModel.getTotal_views()+ " Views";
        binding.tvViews.setText(text);

        if (!reelModel.getUserImage().isEmpty())
            Glide.with(getContext())
                    .load(reelModel.getUserPath() + reelModel.getUserImage())
                    .error(R.drawable.ic_user)
                    .placeholder(R.drawable.ic_user)
                    .into(binding.icUserImages)
                    .onLoadFailed(getContext().getDrawable(R.drawable.ic_user));
        binding.cardFollow.setOnClickListener(view -> followUnfollow(reelModel.getUserId()));
        binding.tvUserName.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", reelModel.getUserId())));
        binding.icUserImages.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", reelModel.getUserId())));
        binding.commentLay.setOnClickListener(view -> startActivity(new Intent(getContext(), ReelCommentActivity.class).putExtra("reel_id", reelModel.getId())));


        isLiked = reelModel.getiLiked() != 0;
        likeCount = reelModel.getLikeCount();
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
        if (reelModel.getiLiked() == 1)
            binding.likeImg.setImageResource(R.drawable.ic_likes_selected);
        else binding.likeImg.setImageResource(R.drawable.ic_likes);

        binding.shareLay.setOnClickListener(view -> shareReel());

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SlideModel model = null;
            if (i == 0) if (!reelModel.getFile().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getFile(), ScaleTypes.CENTER_INSIDE);
            if (i == 1) if (!reelModel.getImage2().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage2(), ScaleTypes.CENTER_INSIDE);
            if (i == 2) if (!reelModel.getImage3().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage3(), ScaleTypes.CENTER_INSIDE);
            if (i == 3) if (!reelModel.getImage4().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage4(), ScaleTypes.CENTER_INSIDE);
            if (i == 4) if (!reelModel.getImage5().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage5(), ScaleTypes.CENTER_INSIDE);
            if (i == 5) if (!reelModel.getImage6().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage6(), ScaleTypes.CENTER_INSIDE);
            if (i == 6) if (!reelModel.getImage7().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage7(), ScaleTypes.CENTER_INSIDE);
            if (i == 7) if (!reelModel.getImage8().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage8(), ScaleTypes.CENTER_INSIDE);
            if (i == 8) if (!reelModel.getImage9().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage9(), ScaleTypes.CENTER_INSIDE);
            if (i == 9) if (!reelModel.getImage10().equalsIgnoreCase(""))
                model = new SlideModel(reelModel.getReelPath() + reelModel.getImage10(), ScaleTypes.CENTER_INSIDE);

            if (model != null) slideModels.add(model);
        }

        if (slideModels.size() > 1) {
            binding.postImageSlider.setVisibility(View.VISIBLE);
            binding.postImage.setVisibility(View.GONE);
            binding.postImageSlider.setImageList(slideModels);
        } else {
            binding.postImage.setVisibility(View.VISIBLE);
            binding.postImageSlider.setVisibility(View.GONE);
            Glide.with(getContext()).load(reelModel.getReelPath() + reelModel.getFile()).into(binding.postImage);
        }
        viewReel();

        return binding.getRoot();
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

    private void shareReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.shareReel(session.getUser_Id(), reelModel.getId()).enqueue(new Callback<CommonResModel>() {
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

    private void viewReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.viewReel(session.getUser_Id(), reelModel.getId()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getMsg().equalsIgnoreCase("view successfuly")){
                                String text = reelModel.getTotal_views() + 1 + " Views";
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
    private void likeReel() {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.likeReel(session.getUser_Id(), reelModel.getId()).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getMsg().equalsIgnoreCase("Liked")) {
                                    reelModel.setiLiked(1);
                                    reelModel.setLikeCount(reelModel.getLikeCount() + 1);
                                } else {
                                    reelModel.setiLiked(0);
                                    reelModel.setLikeCount(reelModel.getLikeCount() - 1);
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

                binding.tvLikeCount.setText(String.valueOf(reelModel.getLikeCount()));
                if (reelModel.getiLiked() == 1) {
                    binding.likeImg.setImageResource(R.drawable.ic_likes_selected);
                } else {
                    binding.likeImg.setImageResource(R.drawable.ic_likes);
                }
                binding.tvLikeCount.setText(binding.tvLikeCount.getText().toString());
                reelModel.setLikeCount(Integer.parseInt(binding.tvLikeCount.getText().toString()));
            }
        });
    }

    private void dialogThreeDots() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_three_dots);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue_curve_background);
        dialog.show();
    }

}