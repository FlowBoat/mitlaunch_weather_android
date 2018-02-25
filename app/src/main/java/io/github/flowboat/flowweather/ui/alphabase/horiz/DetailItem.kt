package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import io.github.flowboat.flowweather.R

data class DetailItem(val detailType: DetailType,
                      val value: String): AbstractFlexibleItem<DetailHolder>() {
    override fun getLayoutRes() = R.layout.item_detail

    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>,
                                holder: DetailHolder,
                                position: Int,
                                payloads: MutableList<Any>?) {
        holder.onSetValues(this)
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
            = DetailHolder(view, adapter)
}

enum class DetailType(val icon: String, val text: Int) {
    PRECIPITATION("wic-umbrella", R.string.detail_precipitation),
    PRESSURE("wic-barometer", R.string.detail_pressure),
    HUMIDITY("wic-humidity", R.string.detail_humidity),
    AIR_QUALITY("wic-windy", R.string.detail_air_quality),
    WIND_SPEED("wic-strong-wind", R.string.detail_wind_speed),
    WIND_DIR("wic-wind-direction", R.string.detail_wind_dir),
    SEASON("wic-stars", R.string.detail_season),
    UV_INDEX("wic-day-sunny", R.string.detail_uv_index)
}