package com.caracode.whatclothes.main

import android.support.v7.widget.RecyclerView


interface RecyclerAdapterInterface<in RI : RecyclerInputModel, in VH: RecyclerView.ViewHolder> {

    fun setRecyclerItems(items: List<RI>)

    fun onBindViewHolder(holder: VH, position: Int)


}
