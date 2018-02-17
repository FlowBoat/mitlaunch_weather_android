package io.github.flowboat.flowweather.wox

/**
 * Created by nulldev on 2/16/18.
 */
data class WoxWeatherCombo(
        val before1: WoxWeatherState,
        val before2: WoxWeatherState,
        val current: WoxWeatherState,
        val after1: WoxWeatherState,
        val after2: WoxWeatherState,
        val clazz: String,
        val message: List<String>
)