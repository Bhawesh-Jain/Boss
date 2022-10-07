package com.boss.view.activity;

import static com.boss.UtilityTools.Utils.getRandomColor;
import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.adapter.ReelCommentAdapter;
import com.boss.databinding.ActivityReelCommentBinding;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.ReelCommentModel;
import com.boss.util.Constants;
import com.boss.util.ProgressDialog;
import com.boss.util.Session;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReelCommentActivity extends AppCompatActivity {

    private final Activity activity = ReelCommentActivity.this;
    private ActivityReelCommentBinding binding;
    private String reelId;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReelCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(activity);

        binding.commentPost.setOnClickListener(view -> addComment(binding.commentEdt.getText().toString()));
        reelId = getIntent().getStringExtra(Constants.Key.reel_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCommentData();
    }

    private void addComment(String comment) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(this);

        apiService.addComment(session.getUser_Id(), reelId, comment).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                loadComments();
                                binding.commentEdt.setText("");
                            } else
                                Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
                progressDialog.dismiss();
            }
        });

    }

    private void loadComments() {
        ApiService apiService = RetrofitClient.getClient(this);

        apiService.loadReelComments(reelId).enqueue(new Callback<ReelCommentModel>() {
            @Override
            public void onResponse(@NonNull Call<ReelCommentModel> call, @NonNull Response<ReelCommentModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                binding.commentsRecyclerView.setAdapter(new ReelCommentAdapter(activity, response.body().getData()));
                            } else
                                Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReelCommentModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void loadCommentData() {
        ApiService apiService = RetrofitClient.getClient(this);

        apiService.loadReelComments(reelId).enqueue(new Callback<ReelCommentModel>() {
            @Override
            public void onResponse(@NonNull Call<ReelCommentModel> call, @NonNull Response<ReelCommentModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                ReelCommentModel.UserData userData = response.body().getUserData();
                                binding.textUserName.setText(userData.getUserName());
                                binding.tvDescription.setText(userData.getDescription());
                                if (userData.getUserImage().isEmpty()) {
                                    binding.imageUserProfile.setVisibility(View.GONE);
                                    binding.textUserImgCard.setVisibility(View.VISIBLE);
                                    binding.textUserImgCard.setCardBackgroundColor(getRandomColor());
                                    binding.textUserImg.setText(Character.toString(userData.getUserName().charAt(0)).toUpperCase());
                                } else {
                                    binding.imageUserProfile.setVisibility(View.VISIBLE);
                                    binding.textUserImgCard.setVisibility(View.GONE);
                                    Glide.with(activity)
                                            .load(response.body().getUserPath() + userData.getUserImage())
                                            .error(R.drawable.ic_user)
                                            .placeholder(R.drawable.ic_user)
                                            .into(binding.imageUserProfile)
                                            .onLoadFailed(AppCompatResources.getDrawable(activity, R.drawable.ic_user));
                                }

                                binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                binding.commentsRecyclerView.setAdapter(new ReelCommentAdapter(activity, response.body().getData()));
                            } else
                                Toast.makeText(activity, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReelCommentModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }
}