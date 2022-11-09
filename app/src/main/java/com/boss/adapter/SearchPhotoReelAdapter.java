package com.boss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.ItemMyLikeReelProfileBinding;
import com.boss.model.Response_Models.HomeReelModel;
import com.boss.model.Response_Models.MyLikeReelModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class SearchPhotoReelAdapter extends RecyclerView.Adapter<SearchPhotoReelAdapter.ViewHolder> {
    private final Context context;
    private final List<HomeReelModel.Datum> data;
    private final String path;

    public SearchPhotoReelAdapter(Context context, List<HomeReelModel.Datum> data, String path) {
        this.context = context;
        this.data = data;
        this.path = path;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMyLikeReelProfileBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            HomeReelModel.Datum current = data.get(position);

            Glide.with(context).load(path + current.getFile()).error(R.color.black).into(holder.binding.imageThumbnail);

            holder.binding.textCount.setText(String.valueOf(current.getLikeCount()));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemMyLikeReelProfileBinding binding;

        public ViewHolder(@NonNull ItemMyLikeReelProfileBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}