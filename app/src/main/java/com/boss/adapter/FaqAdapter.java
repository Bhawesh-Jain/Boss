package com.boss.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.model.Response_Models.FaqModel;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    private final List<FaqModel.Datum> list;
    private final Context context;

    public FaqAdapter(List<FaqModel.Datum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(Html.fromHtml(list.get(position).getQuestion(), Html.FROM_HTML_MODE_COMPACT));
        holder.desc.setText(Html.fromHtml(list.get(position).getAnswer(), Html.FROM_HTML_MODE_COMPACT));

        holder.item_lay.setOnClickListener(view -> {
            if (!list.get(position).isVisible()){
                holder.ic_arrow.setImageResource(R.drawable.ic_arrow_up);
                holder.desc.setVisibility(View.VISIBLE);
                list.get(holder.getAdapterPosition()).setVisible(true);
            } else {
                holder.ic_arrow.setImageResource(R.drawable.ic_arrow_down);
                holder.desc.setVisibility(View.GONE);
                list.get(holder.getAdapterPosition()).setVisible(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, desc;
        private ImageView ic_arrow;
        private RelativeLayout item_lay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);

            ic_arrow = itemView.findViewById(R.id.ic_arrow);

            item_lay = itemView.findViewById(R.id.item_lay);
        }
    }
}
