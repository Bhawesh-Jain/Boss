package com.boss.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.boss.R;
import com.boss.model.Response_Models.PrivacyPolModel;
import com.boss.model.TermsConditionModel;

import java.util.List;

public class TermsConditionAdapter extends RecyclerView.Adapter<TermsConditionAdapter.ViewHolder> {
    private final List<TermsConditionModel.Datum> list;
    private final Context context;

    public TermsConditionAdapter(List<TermsConditionModel.Datum> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_item_lay, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTv.setText(Html.fromHtml(list.get(position).getTermConditionText(), Html.FROM_HTML_MODE_COMPACT));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTv = itemView.findViewById(R.id.tv_item);
        }
    }
}
