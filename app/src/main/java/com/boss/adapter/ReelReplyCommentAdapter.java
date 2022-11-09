package com.boss.adapter;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ItemCommentsReplyBinding;
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

public class ReelReplyCommentAdapter extends RecyclerView.Adapter<ReelReplyCommentAdapter.ViewHolder> {
    private final Context context;
    private final List<ReelCommentModel.Datum.Reply> list;
    private final String path;
    private final Session session;
    private final CommentInterface commentInterface;
    private final String commentId;

    public ReelReplyCommentAdapter(Context context, List<ReelCommentModel.Datum.Reply> list, String path, CommentInterface commentInterface, String commentId) {
        this.context = context;
        this.list = list;
        this.path = path;
        this.commentInterface = commentInterface;
        this.commentId = commentId;
        this.session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentsReplyBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReelCommentModel.Datum.Reply currentModel = list.get(position);

        Glide.with(context).load(path + currentModel.getUserImage())
                .error(R.drawable.ic_user)
                .into(holder.binding.imageUserProfile)
                .onLoadFailed(AppCompatResources.getDrawable(context, R.drawable.ic_user));

        if (currentModel.getI_liked() == 0) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
        } else if (currentModel.getI_liked() == 1) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
        }

        if (session.getUser_Id().equalsIgnoreCase(currentModel.getUser_id()))
            holder.binding.imageDelete.setVisibility(View.VISIBLE);

        holder.binding.textCommentTime.setText(currentModel.getAgoTime());

        //holder.binding.textComment.setText(currentModel.getComment());

        setTags(holder.binding.textComment, currentModel.getComment(), commentId, currentModel.getUserName());

        holder.binding.textUserName.setText(currentModel.getUserName());
        holder.binding.textCommentLikeCount.setText(String.valueOf(currentModel.getTotal_like()));
        holder.binding.imageDelete.setOnClickListener(view -> deleteComment(currentModel.getId(), holder.getAdapterPosition()));

        holder.binding.textReply.setOnClickListener(view -> commentInterface.onReply(commentId, currentModel.getUserName()));
        holder.binding.textUserName.setOnClickListener(view -> context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("id", currentModel.getUser_id())));
        holder.binding.imageUserProfile.setOnClickListener(view -> context.startActivity(new Intent(context, UserProfileActivity.class).putExtra("id", currentModel.getUser_id())));

        holder.binding.imageLike.setOnClickListener(view -> {
                    likeUnlikeComments(session.getUser_Id(), currentModel.getId(), holder.binding.imageLike, holder.binding.textCommentLikeCount);
                    if (holder.binding.imageLike.getDrawable() == AppCompatResources.getDrawable(context, R.drawable.ic_heart_filled))
                        holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
                    else holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
                }
        );
    }

    private void likeUnlikeComments(String user_id, String like_id, ImageView imageLike, TextView textCommentLikeCount) {
        ApiService apiService = RetrofitClient.getClient(context);

        apiService.likeUnlikeReelReplyComment(user_id, like_id).enqueue(new Callback<LikeUnlikeResModel>() {
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
                    notifyItemChanged(list.size() - 1);
                    list.remove(position);

                    ApiService apiService = RetrofitClient.getClient(context);

                    apiService.deleteReelComment(id, "main").enqueue(new Callback<CommonResModel>() {
                        @Override
                        public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                            dialogInterface.dismiss();
                            if (response.code() == 200) {
                                if (response.body() != null) {
                                    try {
                                        if (response.body().getResult().equalsIgnoreCase("false")) {
                                            Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
                                        }
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

    private void setTags(TextView pTextView, String pTagString, String commentId, String userName) {
        SpannableString string = new SpannableString(pTagString);

        int start = -1;
        for (int i = 0; i < pTagString.length(); i++) {
            if (pTagString.charAt(i) == '@') {
                start = i;
            } else if (pTagString.charAt(i) == ' ' || pTagString.charAt(i) == '\n' || (i == pTagString.length() - 1 && start != -1)) {
                if (start != -1) {
                    if (i == pTagString.length() - 1) {
                        i++; // case for if hash is last word and there is no
                        // space after word
                    }

                    string.setSpan(new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            commentInterface.onReply(commentId, userName);
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            // link color
                            ds.setColor(Color.parseColor("#33b5e5"));
                            ds.setUnderlineText(false);
                        }
                    }, start, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = -1;
                }
            }
        }

        pTextView.setMovementMethod(LinkMovementMethod.getInstance());
        pTextView.setText(string);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCommentsReplyBinding binding;

        public ViewHolder(ItemCommentsReplyBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}