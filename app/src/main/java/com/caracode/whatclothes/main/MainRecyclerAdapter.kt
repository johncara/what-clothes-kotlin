package com.caracode.whatclothes.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.caracode.whatclothes.R
import java.util.*

class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.MainRecyclerHolder>() {

    private var dayModels: List<DayModel> = ArrayList()

    fun setDayModels(dayModels: List<DayModel>) {
        this.dayModels = dayModels
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainRecyclerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return MainRecyclerHolder(view)
    }

    override fun onBindViewHolder(holder: MainRecyclerHolder, position: Int) {
        holder.bind(dayModels[position])
    }

    override fun getItemCount(): Int {
        return dayModels.size
    }

    class MainRecyclerHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.iv_main)
        lateinit var ivMain: ImageView
        @BindView(R.id.tv_date_time)
        lateinit var tvDateTime: TextView
        @BindView(R.id.tv_max_temp)
        lateinit var tvMaxTemp: TextView
        @BindView(R.id.tv_min_temp)
        lateinit var tvMinTemp: TextView
        @BindView(R.id.iv_clothing_upper)
        lateinit var ivClothingUpper: ImageView
        @BindView(R.id.iv_clothing_lower)
        lateinit var ivClothingLower: ImageView

        init {
            ButterKnife.bind(this, itemView)
        }

        internal fun bind(dayModel: DayModel) {
            val context = ivMain.context
            tvDateTime.text = dayModel.readableDate
            tvMaxTemp.text = context.getString(R.string.max_temp_format, dayModel.maxTemperature)
            tvMinTemp.text = context.getString(R.string.min_temp_format, dayModel.minTemperature)
            Glide.with(context).load(dayModel.photoUrl).into(ivMain!!)
            ivClothingUpper.setImageResource(dayModel.clothesRefsUpperLower.first)
            ivClothingLower.setImageResource(dayModel.clothesRefsUpperLower.second)
        }
    }
}
