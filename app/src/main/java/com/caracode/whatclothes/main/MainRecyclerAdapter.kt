package com.caracode.whatclothes.main

import android.support.annotation.LayoutRes
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
import kotlin.reflect.KFunction1

class MainRecyclerAdapter<
        in IM : RecyclerInputModel,
        in OM : RecyclerOutputModel,
        RH>
    (private val customAdapter: CustomAdapter<IM, OM>,
     private val viewHolderConstructor: KFunction1<View, RH>,
     @param:LayoutRes private val layoutId: Int)
    : RecyclerView.Adapter<RH>(), MainCustomAdapter.MainAdapterCallback
        where RH : MainRecyclerAdapter.RecyclerHolderInterface<OM>,
        RH : RecyclerView.ViewHolder {

    override fun dataSetChanged() {
        notifyDataSetChanged()
    }

    fun setInputModels(inputModels: List<IM>) {
        customAdapter.setInputModels(inputModels)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RH {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return viewHolderConstructor.invoke(view)
    }

    override fun onBindViewHolder(holder: RH, position: Int) {
        val dayModel = customAdapter.getOutputModel(position)
        holder.bind(dayModel)
    }

    override fun getItemCount(): Int {
        return customAdapter.modelsSize()
    }

    interface RecyclerHolderInterface<in OM : RecyclerOutputModel> {
        fun bind(outputModel: OM)
    }

    class MainRecyclerHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        RecyclerHolderInterface<DayModel> {

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

        override fun bind(dayModel: DayModel) {
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

