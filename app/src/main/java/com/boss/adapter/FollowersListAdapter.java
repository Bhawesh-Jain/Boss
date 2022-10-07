package com.boss.adapter;

import static com.boss.util.BaseUrl.User_image_Url;
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
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.RelationUserModel;
import com.boss.util.Session;
import com.boss.view.activity.UserProfileActivity;
import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowersListAdapter extends RecyclerView.Adapter<FollowersListAdapter.MyViewHolder> {

    Context context;
    List<RelationUserModel.Datum> followersListModels;
    private final Session session;

    public FollowersListAdapter(Context context, List<RelationUserModel.Datum> followersListModels) {
        this.context = context;
        this.followersListModels = followersListModels;
        session = new Session(context);
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.followers_list_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        if (followersListModels.size() > 0) {
            holder.txtname.setText(followersListModels.get(position).getName());
            Glide.with(context).load(User_image_Url + followersListModels.get(position).getImage())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_user)
                    .into(holder.user_img);

            holder.unfollow_tv.setOnClickListener(view -> dialog(followersListModels.get(position).getName(), followersListModels.get(position).getId(), holder.unfollow_tv));

            holder.txtname.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("id", followersListModels.get(position).getId());

                context.startActivity(intent);
            });
            holder.user_img.setOnClickListener(v -> {
                Intent intent = new Intent(context, UserProfileActivity.class);
                intent.putExtra("id", followersListModels.get(position).getId());

                context.startActivity(intent);
            });

        }
    }

    private void dialog(String name, String id, TextView btn) {
        if (btn.getText().toString().equalsIgnoreCase("Following"))
            new AlertDialog.Builder(context)
                    .setTitle("Unfollow")
                    .setMessage("Confirm to Unfollow " + name)
                    .setPositiveButton("Confirm", (dialogInterface, i) -> {
                        followUnfollow(session.getUser_Id(), id, btn);
                        dialogInterface.dismiss();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        else followUnfollow(session.getUser_Id(), id, btn);
    }

    private void followUnfollow(String userId, String toUserId, TextView btn) {
        ApiService apiService = RetrofitClient.getClient(context);

        apiService.follow_Unfollow(userId, toUserId).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getMsg().equalsIgnoreCase("Followed")) {
                                btn.setText(R.string.following);
                            } else {
                                btn.setText(R.string.follow);
                            }
                        } else
                            Toast.makeText(context, "" + response.body().getMsg(), Toast.LENGTH_SHORT).show();
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
    public int getItemCount() {
        return followersListModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtname, unfollow_tv;
        ImageView user_img;

        public MyViewHolder(@NonNull @NotNull View itemView) {

            super(itemView);
            txtname = itemView.findViewById(R.id.txtname);
            unfollow_tv = itemView.findViewById(R.id.unfollow_tv);
            user_img = itemView.findViewById(R.id.user_img);
        }
    }

}
