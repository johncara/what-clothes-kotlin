package com.caracode.whatclothes.common.recycler

import com.caracode.whatclothes.main.MainCustomAdapter


interface CustomAdapter<in IM : RecyclerAdapterInputModel, out OM : ViewHolderInputModel> {

    fun setInputModels(inputModels: List<IM>)

    fun getOutputModel(position: Int): OM

    fun modelsSize(): Int

    fun setAdapterCallback(adapterCallback: MainCustomAdapter.MainAdapterCallback)
}
