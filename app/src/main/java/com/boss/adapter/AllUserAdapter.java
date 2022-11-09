package com.boss.adapter;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.Retrofit.ApiService;
import com.boss.Retrofit.RetrofitClient;
import com.boss.databinding.ItemUserBinding;
import com.boss.model.NotificationModel;
import com.boss.model.Response_Models.AllUsersModel;
import com.boss.model.Response_Models.CommonResModel;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.util.Session;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ViewHolder> {
    private final Context context;
    private final List<AllUsersModel.Datum> data;
    private final String path;

    public AllUserAdapter(Context context, List<AllUsersModel.Datum> data, String path) {
        this.context = context;
        this.data = data;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllUsersModel.Datum currentModel = data.get(position);

        String myChar = "X";
        String name = currentModel.getName();
        if (!name.equalsIgnoreCase(""))
            myChar = Character.toString(name.charAt(0));

        holder.binding.textUserProfile.setText(myChar);
        if (currentModel.getImage().length() == 0){
            holder.binding.imageUserProfile.setVisibility(GONE);
            holder.binding.textUserProfile.setVisibility(VISIBLE);
        } else {
            holder.binding.imageUserProfile.setVisibility(VISIBLE);
            holder.binding.textUserProfile.setVisibility(GONE);
        }

        holder.binding.textUserName.setText(currentModel.getName().trim());

        Glide.with(context).load(path + currentModel.getImage()).into(holder.binding.imageUserProfile);

        if (currentModel.getiFollowed() == 1) {
            holder.binding.textFollowUnfollow.setText(R.string.following);
        } else holder.binding.textFollowUnfollow.setText(R.string.follow);

        holder.binding.textFollowUnfollowCard.setOnClickListener(v -> followUnfollow(holder.binding.textFollowUnfollow, currentModel.getUserId(), holder.getAdapterPosition()));
    }

    private void followUnfollow(TextView textView, String user_id, int position) {
        ApiService apiService = RetrofitClient.getClient(context);

        Session session = new Session(context);
        apiService.follow_Unfollow(session.getUser_Id(), user_id).enqueue(new Callback<CommonResModel>() {
            @Override
            public void onResponse(@NonNull Call<CommonResModel> call, @NonNull Response<CommonResModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getMsg().equalsIgnoreCase("Followed")) {
                                textView.setText(R.string.following);
                            } else {
                                textView.setText(R.string.follow);
                                data.get(position).setiFollowed(0);
                                notifyItemChanged(position);
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
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemUserBinding binding;

        public ViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
