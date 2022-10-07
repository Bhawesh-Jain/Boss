package com.boss.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.databinding.PopularCreatorItemBinding;
import com.boss.model.Response_Models.PopularCreatorResModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PopularCreatorAdapter extends RecyclerView.Adapter<PopularCreatorAdapter.ViewHolder> {
    private List<PopularCreatorResModel.Datum> list;
    private PopularCreatorResModel model;
    private Context context;

    public PopularCreatorAdapter(PopularCreatorResModel model, Context context) {
        this.model = model;
        this.context = context;
        this.list = model.getData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(PopularCreatorItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PopularCreatorResModel.Datum currentModel = list.get(position);
        holder.binding.textUserName.setText(currentModel.getName());
        if (currentModel.getiFollowed() == 0)
        holder.binding.textFollowUnfollow.setText(R.string.follow);
        else holder.binding.textFollowUnfollow.setText(R.string.following);

        String myChar = "X";
        String name = currentModel.getName();
        if (!name.equalsIgnoreCase(""))
            myChar = Character.toString(name.charAt(0));

        holder.binding.textUserProfile.setText(myChar);
        if (currentModel.getImage().length() == 0){
            holder.binding.imageUserProfile.setVisibility(View.GONE);
            holder.binding.textUserProfile.setVisibility(View.VISIBLE);
        } else {
            holder.binding.imageUserProfile.setVisibility(View.VISIBLE);
            holder.binding.textUserProfile.setVisibility(View.GONE);
        }

        Glide.with(context).load(model.getPath()+currentModel.getImage()).into(holder.binding.imageUserProfile);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public PopularCreatorItemBinding binding;

        public ViewHolder(PopularCreatorItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
