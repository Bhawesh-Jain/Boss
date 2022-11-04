package com.boss.adapter;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.ItemBannerBinding;
import com.boss.model.Response_Models.BannerResModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    Context context;
    List<BannerResModel.Datum> list;
    String path;

    public BannerAdapter(Context context, List<BannerResModel.Datum> list, String path) {
        this.context = context;
        this.path = path;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBannerBinding binding = ItemBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        BannerResModel.Datum currentModel = list.get(position);

        Log.d(TAG, "onBindViewHolder() called with: path = [" + path + currentModel.getImage() + "], position = [" + position + "]");

        Glide.with(context).load(path + currentModel.getImage())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.binding.imageBanner)
                .onLoadFailed(AppCompatResources.getDrawable(context, R.drawable.ic_error_outline_white_48dp));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding binding;

        public MyViewHolder(@NonNull ItemBannerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
