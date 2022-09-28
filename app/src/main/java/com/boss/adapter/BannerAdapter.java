package com.boss.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.databinding.ItemBannerBinding;

import java.util.ArrayList;


public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> list;

    public BannerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
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
     //   NotificationModel notificationModels=list.get(position);
  /*      holder.binding.Name.setText(notificationModels.getName());
        holder.binding.UserId.setText(notificationModels.getId());
        holder.binding.desc.setText(notificationModels.getDesc());
        holder.binding.ivUserImage.setImageResource(notificationModels.getImage());
  */  }

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
