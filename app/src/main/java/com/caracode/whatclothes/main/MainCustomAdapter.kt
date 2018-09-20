package com.caracode.whatclothes.main

import java.util.*


class MainCustomAdapter : CustomAdapter<DayModel, DayModel> {

    private var dayModels: List<DayModel> = ArrayList()

    private lateinit var mainAdapterCallback: MainAdapterCallback

    override fun setAdapterCallback(adapterCallback: MainAdapterCallback) {
        this.mainAdapterCallback = adapterCallback
    }

    override fun setInputModels(dayModels: List<DayModel>) {
        this.dayModels = dayModels
        mainAdapterCallback.dataSetChanged()
    }

    override fun getOutputModel(position: Int): DayModel {
        return dayModels[position]
    }

    override fun modelsSize(): Int {
        return dayModels.size
    }

    interface MainAdapterCallback {

        fun dataSetChanged()
    }
}
