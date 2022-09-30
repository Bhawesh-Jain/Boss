package com.boss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.ItemCommentsBinding;
import com.boss.model.Response_Models.ReelCommentModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ReelCommentAdapter extends RecyclerView.Adapter<ReelCommentAdapter.ViewHolder> {
    private final Context context;
    private List<ReelCommentModel.Datum> reelComments = new ArrayList<>();
//    private final Session session;

    public ReelCommentAdapter(Context context, List<ReelCommentModel.Datum> reelComments) {
        this.context = context;
        this.reelComments = reelComments;
//        session = new Session(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCommentsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(reelComments.get(position).getPath() + reelComments.get(position).getUserImage())
                .error(R.drawable.ic_user)
                .into(holder.binding.imageUserProfile)
                .onLoadFailed(AppCompatResources.getDrawable(context, R.drawable.ic_user));
/*

        String User_ID = session.getUser_Id();
        String like_id = reelComments.get(position).getId();

        if (reelComments.get(position).getI_liked().equalsIgnoreCase("0")) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
        } else if (reelComments.get(position).getI_liked().equalsIgnoreCase("1")) {
            holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
        }
*/

        holder.binding.textCommentTime.setText(reelComments.get(position).getCreatedDate());
        holder.binding.textComment.setText(reelComments.get(position).getComment());
//        holder.binding.textCommentLikeCount.setText(reelComments.get(position).getTotalLikes());
        holder.binding.textUserName.setText(reelComments.get(position).getUserName());

/*
        holder.binding.imageLike.setOnClickListener(view -> {
                    likeUnlikeComments(User_ID, like_id, holder.binding.imageLike, holder.binding.textCommentLikeCount, holder.getAdapterPosition());
                    if (holder.binding.imageLike.getDrawable() == AppCompatResources.getDrawable(context, R.drawable.ic_heart_filled)) {
                        holder.binding.imageLike.setImageResource(R.drawable.ic_heart);
                        holder.binding.textCommentLikeCount.setText(String.valueOf(Integer.parseInt(reelComments.get(holder.getAdapterPosition()).getTotal_likes()) - 1));
                    } else {
                        holder.binding.textCommentLikeCount.setText(String.valueOf(Integer.parseInt(reelComments.get(holder.getAdapterPosition()).getTotal_likes()) + 1));
                        holder.binding.imageLike.setImageResource(R.drawable.ic_heart_filled);
                    }
                }
        );
*/
    }

//    private void likeUnlikeComments(String user_id, String like_id, ImageView imageLike, TextView textCommentLikeCount, int adapterPosition) {
//
//    }

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
