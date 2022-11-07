package com.boss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.ItemMyViewReelProfileBinding;
import com.boss.model.Response_Models.MyLikeReelModel;
import com.boss.model.Response_Models.MyViewsReelModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class MyLikedReelAdapter extends RecyclerView.Adapter<MyLikedReelAdapter.ViewHolder> {
    private final Context context;
    private final List<MyLikeReelModel.Datum> data;
    private final String path;

    public MyLikedReelAdapter(Context context, List<MyLikeReelModel.Datum> data, String path) {
        this.context = context;
        this.data = data;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMyViewReelProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            MyLikeReelModel.Datum current = data.get(position);

            Glide.with(context).load(path + current.getThumbnail()).error(R.color.black).into(holder.binding.imageThumbnail);

//            holder.binding.textCount.setText(String.valueOf(current.getLikedCount()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemMyViewReelProfileBinding binding;

        public ViewHolder(@NonNull ItemMyViewReelProfileBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
