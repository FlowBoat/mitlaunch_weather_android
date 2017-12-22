package io.github.flowboat.flowweather.ui.datawindow

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.flowboat.flowweather.R
import org.threeten.bp.ZonedDateTime

class VolatileDW : AbstractFlexibleItem<DWHolder>() {
    /**
     * {@inheritDoc}
     */
    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
        = DWHolder(view, adapter)

    /**
     * {@inheritDoc}
     */
    override fun getLayoutRes() = R.layout.item_dw

    /**
     * {@inheritDoc}
     */
    override fun bindViewHolder(adapter: FlexibleAdapter<*>,
                                holder: DWHolder,
                                position: Int,
                                payloads: List<Any>?) {
        holder.onSetValues(this)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VolatileDW

        if (day != other.day) return false
        if (high != other.high) return false
        if (low != other.low) return false
        if (precip != other.precip) return false
        if (data != other.data) return false
        if (warnings != other.warnings) return false

        return true
    }

    override fun hashCode(): Int {
        var result = day?.hashCode() ?: 0
        result = 31 * result + (high ?: 0)
        result = 31 * result + (low ?: 0)
        result = 31 * result + (precip?.hashCode() ?: 0)
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (warnings?.hashCode() ?: 0)
        return result
    }

    var day: ZonedDateTime? = null

    var high: Int? = null

    var low: Int? = null

    var precip: PrecipEstimation? = null

    /*
    var wind: WindEstimation? = null

    var humidity: Float? = null

    var pressure: Int? = null

    var airQuality: Float? = null*/

    var data: List<DayDataComponent>? = null

    var warnings: List<WeatherWarning>? = null
}

enum class WeatherWarning {
    SANDSTORM,
    FLOOD,
    BLIZZARD,
    HURRICANE,

    ALIEN_INVASION
}

enum class PrecipEstimation(val displayName: String, val icon: String) {
    NONE("Clear skies", "wi_day_sunny"),

    PARTLY_CLOUDY("Partly cloudy", "wi_day_cloudy"),
    CLOUDY("Cloudy", "wi_cloudy"),

    DRIZZLE("Light drizzles", "wi_day_showers"),
    SHOWER("Moderate showers", "wi_showers"),
    DOWNPOUR("Heavy downpour", "wi_rain"),

    SNOWY("Snowy", "wi_snow"),
    HAIL("Hail", "wi_hail")
}

data class WindEstimation(val direction: Direction,
                          val speed: Int)

enum class Direction {
    N,
    NE,
    NW,
    E,
    EN,
    ES,
    S,
    SE,
    SW,
    W,
    WS,
    WN
}

class DayDataComponent(val name: String,
                       val data: List<Pair<ZonedDateTime, Double>>)