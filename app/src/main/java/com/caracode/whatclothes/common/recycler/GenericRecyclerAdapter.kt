package com.caracode.whatclothes.common.recycler

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.caracode.whatclothes.main.MainCustomAdapter
import kotlin.reflect.KFunction1

class GenericRecyclerAdapter<
        in AIM : RecyclerAdapterInputModel,
        in HIM : ViewHolderInputModel,
        RH
        >(private val customAdapter: CustomAdapter<AIM, HIM>,
          private val viewHolderConstructor: KFunction1<View, RH>,
          @param:LayoutRes private val layoutId: Int)
    : RecyclerView.Adapter<RH>(), MainCustomAdapter.MainAdapterCallback
        where RH : RecyclerHolderInterface<HIM>,
              RH : RecyclerView.ViewHolder {
    init {
        customAdapter.setAdapterCallback(this)
    }

    override fun dataSetChanged() {
        notifyDataSetChanged()
    }

    fun setInputModels(inputModels: List<AIM>) {
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
}

