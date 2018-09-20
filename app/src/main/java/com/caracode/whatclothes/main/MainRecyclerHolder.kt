package com.caracode.whatclothes.main

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.caracode.whatclothes.R
import com.caracode.whatclothes.common.recycler.RecyclerHolderInterface
import kotlinx.android.synthetic.main.item_day.view.*


class MainRecyclerHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        RecyclerHolderInterface<DayModel> {

    private val ivMain = itemView.iv_main
    private val tvDateTime = itemView.tv_date_time
    private val tvMaxTemp = itemView.tv_max_temp
    private val tvMinTemp = itemView.tv_min_temp
    private val ivClothingUpper = itemView.iv_clothing_upper
    private val ivClothingLower= itemView.iv_clothing_lower

    override fun bind(model: DayModel) {
        val context = ivMain.context
        tvDateTime.text = model.readableDate
        tvMaxTemp.text = context.getString(R.string.max_temp_format, model.maxTemperature)
        tvMinTemp.text = context.getString(R.string.min_temp_format, model.minTemperature)
        Glide.with(context).load(model.photoUrl).into(ivMain!!)
        ivClothingUpper.setImageResource(model.clothesRefsUpperLower.first)
        ivClothingLower.setImageResource(model.clothesRefsUpperLower.second)
    }
}
