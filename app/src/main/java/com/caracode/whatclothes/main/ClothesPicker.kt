package com.caracode.whatclothes.main

import com.caracode.whatclothes.R

internal enum class ClothesPicker(private val minTempAbove: Double?,
                                  private val maxTempAbove: Double?,
                                  private val minTempBelow: Double?,
                                  private val maxTempBelow: Double?,
                                  private val drawableRes: Int) {

    // ---------------------  UPPER WEAR  ----------------------------------
    TShirtWeather(15.0, 20.0, null, null, R.drawable.tshirt),
    JacketWeather(5.0, null, null, 12.0, R.drawable.jacket),
    CoatWeather(null, null, 5.0, null, R.drawable.coat),

    // ---------------------  LOWER WEAR  ----------------------------------
    ShortsWeather(17.0, 22.0, null, null, R.drawable.shorts);

    companion object {
        fun getClothesUpperLower(maxTemp: Double, minTemp: Double): Pair<Int, Int> {
            val upper = if (minTemp > TShirtWeather.minTempAbove!! && maxTemp > TShirtWeather.maxTempAbove!!) {
                TShirtWeather.drawableRes
            } else if (minTemp > JacketWeather.minTempAbove && maxTemp < JacketWeather.maxTempBelow!!) {
                JacketWeather.drawableRes
            } else if (minTemp < CoatWeather.minTempBelow!!) {
                CoatWeather.drawableRes
            } else {
                R.drawable.jumper
            }

            val lower = if (minTemp > ShortsWeather.minTempAbove && maxTemp > ShortsWeather.maxTempAbove!!) {
                ShortsWeather.drawableRes
            } else {
                R.drawable.trousers
            }

            return Pair(upper, lower)
        }
    }

}
