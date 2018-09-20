package com.caracode.whatclothes.main


interface CustomAdapter<in IM : RecyclerInputModel, out OM : RecyclerOutputModel> {

    fun setInputModels(dayModels: List<IM>)

    fun getOutputModel(position: Int): OM

    fun modelsSize(): Int

    fun setAdapterCallback(adapterCallback: MainCustomAdapter.MainAdapterCallback)
}
