package com.boss.view.homepage;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.FragmentTextReelBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.Session;
import com.boss.view.activity.ReelCommentActivity;
import com.boss.view.activity.UserProfileActivity;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TextReelFragment extends Fragment {
    private FragmentTextReelBinding binding;
    private final HomeReelModel.Datum model;
    private Session session;
    private boolean isLiked;
    private int likeCount;

    public TextReelFragment(HomeReelModel.Datum model) {
        this.model = model;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTextReelBinding.inflate(inflater, container, false);
        session = new Session(getContext());
        if (getContext() != null) {
            if (model.getTextcolor().equalsIgnoreCase("blue")) {
                binding.onlyTextMessage.setBackgroundColor(getContext().getColor(R.color.light_blue_ap));
            } else if (model.getTextcolor().equalsIgnoreCase("red")) {
                binding.onlyTextMessage.setBackgroundColor(getContext().getColor(R.color.red_ap));
            } else if (model.getTextcolor().equalsIgnoreCase("green")) {
                binding.onlyTextMessage.setBackgroundColor(getContext().getColor(R.color.green_ap));
            } else if (model.getTextcolor().equalsIgnoreCase("purple")) {
                binding.onlyTextMessage.setBackgroundColor(getContext().getColor(R.color.purple_ap));
            } else if (model.getTextcolor().equalsIgnoreCase("yellow")) {
                binding.onlyTextMessage.setBackgroundColor(getContext().getColor(R.color.yellow_ap));
            }
        }
        binding.onlyTextMessage.setText(model.getImageText());
        binding.cardFollow.setOnClickListener(view -> followUnfollow(model.getUserId()));
        binding.tvUserName.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", model.getUserId())));
        binding.icUserImages.setOnClickListener(view -> startActivity(new Intent(getContext(), UserProfileActivity.class).putExtra("id", model.getUserId())));
        binding.commentLay.setOnClickListener(view -> startActivity(new Intent(getContext(), ReelCommentActivity.class).putExtra("reel_id", model.getId())));
        binding.reportAbuseImg.setOnClickListener(view -> dialogThreeDots());

        
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

        binding.shareLay.setOnClickListener(view -> shareReel());

        binding.tvUserName.setText(model.getName());
        binding.tvCommentCount.setText(String.valueOf(model.getCommentCount()));
        binding.tvLikeCount.setText(String.valueOf(model.getLikeCount()));
        binding.tvDesc.setText(model.getDescription());
        String text = model.getTotal_views()+ " Views";
        binding.tvViews.setText(text);


        if (!model.getUserImage().isEmpty())
            if (getContext() != null)
            Glide.with(getContext())
                    .load(model.getUserPath() + model.getUserImage())
                    .error(R.drawable.ic_user)
                    .placeholder(R.drawable.ic_user)
                    .into(binding.icUserImages)
                    .onLoadFailed(AppCompatResources.getDrawable(getContext(), R.drawable.ic_user));
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
                            if (response.body().getMsg().equalsIgnoreCase("view successfuly")){
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

    private void followUnfollow(String toUserId) {
        ApiService apiService = RetrofitClient.getClient(getContext());

        apiService.follow_Unfollow(session.getUser_Id(), toUserId).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getMsg().equalsIgnoreCase("Followed")){
                                    binding.tvFollow.setText(R.string.following);
                                }else binding.tvFollow.setText(R.string.follow);
                            } else Toast.makeText(getContext(), "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
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

    private void dialogThreeDots() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_three_dots);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialogue_curve_background);
        dialog.show();
    }

}