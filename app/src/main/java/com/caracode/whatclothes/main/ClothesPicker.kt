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
            val upper = if (TShirtWeather.minTempAbove.safeLessThan(minTemp) && TShirtWeather.maxTempAbove.safeLessThan(maxTemp)) {
                TShirtWeather.drawableRes
            } else if (JacketWeather.minTempAbove.safeLessThan(minTemp) && JacketWeather.maxTempBelow.safeGreaterThan(maxTemp)) {
                JacketWeather.drawableRes
            } else if (CoatWeather.minTempBelow.safeGreaterThan(minTemp)) {
                CoatWeather.drawableRes
            } else {
                R.drawable.jumper
            }

            val lower = if (ShortsWeather.minTempAbove.safeLessThan(minTemp) &&  ShortsWeather.maxTempAbove.safeLessThan(maxTemp)) {
                ShortsWeather.drawableRes
            } else {
                R.drawable.trousers
            }

            return Pair(upper, lower)
        }
    }

}

private fun Double?.safeLessThan(other: Double): Boolean {
    return this != null && this < other
}

private fun Double?.safeGreaterThan(other: Double): Boolean {
    return this != null && this > other
}
