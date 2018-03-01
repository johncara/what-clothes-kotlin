package com.caracode.whatclothes.main

import android.support.v4.util.Pair

data class MainViewModel(val days: List<DayModel>)


data class DayModel(
        val readableDate: String,
        val minTemperature: Double,
        val maxTemperature: Double,
        val photoUrl: String,
        val clothesRefsUpperLower: Pair<Int, Int>)
