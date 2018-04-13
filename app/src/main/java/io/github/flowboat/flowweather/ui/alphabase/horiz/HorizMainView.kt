package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.content.Context
import android.content.Intent
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.api.hurricane.Hurricane
import io.github.flowboat.flowweather.ui.alphabase.HourItem
import io.github.flowboat.flowweather.ui.alphabase.loc.LocationPickerActivity
import io.github.flowboat.flowweather.ui.alphabase.toDetails
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_horiz_main.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.threeten.bp.Clock
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime

class HorizMainView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        ConstraintLayout(context, attrs) {
    private val hurricane: Hurricane by Kodein.global.lazy.instance()
    private var subscription: Disposable? = null

    fun onCreate() {
        location.onClick {
            val intent = Intent(this@HorizMainView.context, LocationPickerActivity::class.java)
            this@HorizMainView.context.startActivity(intent)
        }

        hourly.setHasFixedSize(true)
        hourly.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        val hourlyAdapter = FlexibleAdapter<HourItem>(emptyList())
        hourly.adapter = hourlyAdapter

        details.setHasFixedSize(true)
        details.layoutManager = LinearLayoutManager(context)

        val detailsAdapter = FlexibleAdapter<DetailItem>(emptyList())
        details.adapter = detailsAdapter

        subscription?.dispose()
        subscription = hurricane.observeForecastToday()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    detailsAdapter.updateDataSet(it.current.toDetails(it.issueDate))

                    temp.text = it.current.mainParameters.temperature.toString() + "°C"
                    high.text = it.current.mainParameters.maximumTemperature.toString() + "°C"
                    low.text = it.current.mainParameters.minimumTemperature.toString() + "°C"

                    val min = it.forecasts.minBy { it.mainParameters.temperature }!!.mainParameters.temperature
                    val max = it.forecasts.maxBy { it.mainParameters.temperature }!!.mainParameters.temperature
                    var lastTemp = (max - min) / 2 + min

                    val hours = it.forecasts.mapIndexedNotNull { index, forecast ->
                        val cal = ZonedDateTime.ofInstant(Instant.ofEpochMilli(forecast.calculationDate.time), Clock.systemDefaultZone().zone)

                        if(cal.dayOfYear == ZonedDateTime.now().dayOfYear) {
                            val oldLastTemp = lastTemp
                            lastTemp = forecast.mainParameters.temperature

                            HourItem(cal.hour,
                                    min,
                                    max,
                                    oldLastTemp,
                                    lastTemp,
                                    index != it.forecasts.lastIndex)
                        } else null
                    }

                    hourlyAdapter.updateDataSet(hours)
                }

        location.text = hurricane.cityCompletor.lookupCity(hurricane.currentCity())
    }

    fun onDestroy() {
        subscription?.dispose()
    }
}
