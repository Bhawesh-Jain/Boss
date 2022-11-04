package com.boss.adapter;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ItemCommentsBinding;
import com.boss.model.CommentInterface;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.LikeUnlikeResModel;
import com.boss.model.Response_Models.ReelCommentModel;
import com.boss.util.Session;
import com.boss.view.activity.UserProfileActivity;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReelCommentAdapter extends RecyclerView.Adapter<ReelCommentAdapter.ViewHolder> {
    private final Context context;
    private final List<ReelCommentModel.Datum> reelComments;
    private final String path;
    private final Session session;
    private final CommentInterface commentInterface;

    public ReelCommentAdapter(Context context, List<ReelCommentModel.Datum> reelComments, CommentInterface commentInterface, String path) {
        this.context = context;
        this.reelComments = reelComments;
        this.commentInterface = commentInterface;
        this.path = path;
        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReelCommentModel.Datum currentModel = reelComments.get(position);

        Glide.with(context).load(path + reelComments.get(position).getUserImage())
                .placeholder(AppCompatResources.getDrawable(context, R.drawable.ic_user))
                .into(holder.binding.imageUserProfile);

        if (currentModel.getiLikedMain() == 0) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
        } else if (currentModel.getiLikedMain() == 1) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
        }

        if (session.getUser_Id().equalsIgnoreCase(currentModel.getUserId()))
            holder.binding.imageDelete.setVisibility(View.VISIBLE);

        holder.binding.imageDelete.setOnClickListener(view -> deleteComment(currentModel.getId(), holder.getAdapterPosition()));
        holder.binding.textUserName.setOnClickListener(view -> context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("id", currentModel.getUserId())));
        holder.binding.imageUserProfile.setOnClickListener(view -> context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("id", currentModel.getUserId())));

        holder.binding.textCommentTime.setText(currentModel.getAgoTime());
        holder.binding.textComment.setText(currentModel.getComment());
        holder.binding.textUserName.setText(currentModel.getUserName());
        holder.binding.textCommentLikeCount.setText(String.valueOf(currentModel.getTotalLikeMain()));

        holder.binding.textReply.setOnClickListener(view -> commentInterface.onReply(currentModel.getId(), currentModel.getUserName()));

        holder.binding.imageLike.setOnClickListener(view -> {
                    likeUnlikeComments(session.getUser_Id(), currentModel.getId(), holder.binding.imageLike, holder.binding.textCommentLikeCount);
                    if (holder.binding.imageLike.getDrawable() == AppCompatResources.getDrawable(context, R.drawable.ic_heart_filled)) {
                        holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
                    } else {
                        holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
                    }
                }
        );

        if (currentModel.getReply().size() != 0) {
            ReelReplyCommentAdapter adapter = new ReelReplyCommentAdapter(context, currentModel.getReply(), path, commentInterface, currentModel.getId());
            holder.binding.subCommentRecycler.setAdapter(adapter);
            holder.binding.subCommentRecycler.setLayoutManager(new LinearLayoutManager(context));
        }
    }

    private void likeUnlikeComments(String user_id, String like_id, ImageView imageLike, TextView textCommentLikeCount) {
        ApiService apiService = RetrofitClient.getClient(context);

        apiService.likeUnlikeReelMainComment(user_id, like_id).enqueue(new Callback<LikeUnlikeResModel>() {
            @Override
            public void onResponse(@NonNull Call<LikeUnlikeResModel> call, @NonNull Response<LikeUnlikeResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        try {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                if (response.body().getMsg().equalsIgnoreCase("Liked")) {
                                    imageLike.setImageResource(R.drawable.ic_heart_filled);
                                } else imageLike.setImageResource(R.drawable.ic_heart);
                                textCommentLikeCount.setText(String.valueOf(response.body().getData()));
                            } else Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LikeUnlikeResModel> call, @NonNull Throwable t) {
                Log.e(TAG, "" + t.getLocalizedMessage());
            }
        });
    }

    private void deleteComment(String id, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Comment")
                .setMessage("Are you sure you want to delete this comment?")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    notifyItemRemoved(position);
                    notifyItemChanged(reelComments.size() - 1);
                    reelComments.remove(position);
                    ApiService apiService = RetrofitClient.getClient(context);

                    apiService.deleteReelComment(id, "main").enqueue(new Callback<CommonResModel>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                            dialogInterface.dismiss();
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    try {
                                        if (response.body().getResult().equalsIgnoreCase("false"))
                                            Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<CommonResModel> call, @NonNull Throwable t) {
                            dialogInterface.dismiss();
                            Log.e(TAG, "" + t.getLocalizedMessage());
                        }
                    });
                }).show();
    }

    @Override
    public int getItemCount() {
        if (reelComments != null)
            return reelComments.size();
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ItemCommentsBinding binding;

        public ViewHolder(@NonNull ItemCommentsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
