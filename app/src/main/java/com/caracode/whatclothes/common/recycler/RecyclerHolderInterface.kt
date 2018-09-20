package com.caracode.whatclothes.common.recycler


interface RecyclerHolderInterface<in VIM : ViewHolderInputModel> {

    fun bind(model: VIM)

}
