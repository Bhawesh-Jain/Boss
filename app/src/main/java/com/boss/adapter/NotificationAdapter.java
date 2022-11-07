package com.boss.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.AdminNotificationItemLayoutBinding;
import com.boss.model.Response_Models.AdminNotiResModel;
import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    private final Context context;
    private final List<AdminNotiResModel.Datum> list;
    private final String path;

    public NotificationAdapter(Context context, List<AdminNotiResModel.Datum> list, String path) {
        this.context = context;
        this.list = list;
        this.path = path;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminNotificationItemLayoutBinding binding = AdminNotificationItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AdminNotiResModel.Datum current = list.get(position);

        holder.binding.textTitle.setText(current.getTitle());
        holder.binding.textTime.setText(current.getCreatedDate());
        holder.binding.textDescription.setText(current.getDescription());

        Glide.with(context)
                .load(path + current.getImage())
                .placeholder(R.drawable.loading)
                .into(holder.binding.imageNotificationIc)
                .onLoadFailed(AppCompatResources.getDrawable(context, R.drawable.error));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public AdminNotificationItemLayoutBinding binding;

        public MyViewHolder(@NonNull AdminNotificationItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
