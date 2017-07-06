package com.caracode.whatclothes.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.caracode.whatclothes.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerHolder> {

    private List<MainViewModel.DayModel> dayModels = new ArrayList<>();

    public void setDayModels(List<MainViewModel.DayModel> dayModels) {
        this.dayModels = dayModels;
        notifyDataSetChanged();
    }

    @Override
    public MainRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent, false);
        return new MainRecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerHolder holder, int position) {
        holder.bind(dayModels.get(position));
    }

    @Override
    public int getItemCount() {
        return dayModels.size();
    }

    public static class MainRecyclerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_main)
        ImageView ivMain;
        @BindView(R.id.tv_date_time)
        TextView tvDateTime;
        @BindView(R.id.tv_max_temp)
        TextView tvMaxTemp;
        @BindView(R.id.tv_min_temp)
        TextView tvMinTemp;

        MainRecyclerHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(MainViewModel.DayModel dayModel) {
            Context context = ivMain.getContext();
            tvDateTime.setText(dayModel.readableDate());
            tvMaxTemp.setText(context.getString(R.string.max_temp_format, dayModel.maxTemperature()));
            tvMinTemp.setText(context.getString(R.string.min_temp_format, dayModel.minTemperature()));
            Glide.with(context).load(dayModel.photoUrl()).into(ivMain);
        }
    }
}
