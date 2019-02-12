package com.caracode.whatclothes.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.caracode.whatclothes.R
import kotlinx.android.synthetic.main.item_day.view.*

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

        init {
            ButterKnife.bind(this, itemView)
        }

        internal fun bind(dayModel: DayModel) {
            itemView.apply {
                val context = iv_main.context
                tv_date_time.text = dayModel.readableDate
                tv_max_temp.text = context.getString(R.string.max_temp_format, dayModel.maxTemperature)
                tv_min_temp.text = context.getString(R.string.min_temp_format, dayModel.minTemperature)
                Glide.with(context).load(dayModel.photoUrl).into(iv_main!!)
                iv_clothing_upper.setImageResource(dayModel.clothesRefsUpperLower.first)
                iv_clothing_lower.setImageResource(dayModel.clothesRefsUpperLower.second)
            }
        }
    }
}
