package io.github.flowboat.flowweather.ui.alphabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.matteobattilana.weather.PrecipType
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.alphabase.horiz.HorizAdapter
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.ui.bridgeui.BridgeUIPresenter
import kotlinx.android.synthetic.main.fragment_alphabase.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(BridgeUIPresenter::class)
class AlphaBaseFragment : BaseRxFragment<AlphaBasePresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_alphabase, container, false)!!

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setToolbarTitle("AlphaBase Test Viewer")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherView.setWeatherData(PrecipType.RAIN)

        pager.adapter = HorizAdapter()

        //Scroll to center (current day)
        pager.setCurrentItem(1, false)

        /*var lastTemp = 20.0
        var lastPrecip = 10.0
        var lastPressure = 101.0
        var lastHumidity = 50.0

        // Add other views
        addStat("Temperature",
                StatCorner.TOP_LEFT,
                R.drawable.ic_thermometer,
                Observable.merge(Observable.just(0), Observable.interval(1, TimeUnit.SECONDS))
                        .map {
                            lastTemp = (lastTemp + Math.random() - 0.5).round(2)
                            lastTemp
                        }
                        .map { "$it°C" })

        addStat("Precipitation",
                StatCorner.TOP_RIGHT,
                R.drawable.ic_drop,
                Observable.merge(Observable.just(0), Observable.interval(5, TimeUnit.SECONDS))
                        .map {
                            lastPrecip = (lastPrecip + Math.random() - 0.5).round(2)
                            lastPrecip
                        }
                        .map { "${it}mm" })

        addStat("Pressure",
                StatCorner.BOTTOM_RIGHT,
                R.drawable.ic_gauge,
                Observable.merge(Observable.just(0), Observable.interval(500, TimeUnit.MILLISECONDS))
                        .map {
                            lastPressure = (lastPressure + Math.random() - 0.5).round(2)
                            lastPressure
                        }
                        .map { "${it}kPa" })

        addStat("Humidity",
                StatCorner.BOTTOM_LEFT,
                R.drawable.ic_humidity,
                Observable.merge(Observable.just(0), Observable.interval(3, TimeUnit.SECONDS))
                        .map {
                            lastHumidity = (lastHumidity + Math.random() - 0.5).round(2)
                            lastHumidity
                        }
                        .map { "$it%" })*/
    }

    /*private fun addStat(name: String,
                        corner: StatCorner,
                        drawable: Int,
                        observable: Observable<String>) {
        val constraint = ConstraintSet()
        constraint.clone(root)

        val iconView = AppCompatImageView(context)
        iconView.setRandomId()
        iconView.setImageResource(drawable)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            iconView.imageAlpha = 200
        root.addView(iconView)

        val titleView = AppCompatTextView(context)
        titleView.setRandomId()
        titleView.text = name
        titleView.setTextColor(Color.WHITE)
        titleView.textSize = 20f
        root.addView(titleView)

        val valueView = EvaporateTextView(context)
        valueView.setRandomId()
        valueView.setTextColor(Color.WHITE)
        valueView.textSize = 20f
        observable.observeOn(AndroidSchedulers.mainThread())
                .autoDisposable(untilStopped())
                .subscribe {
                    valueView.animateText(it)
                }
        root.addView(valueView)

        val from1: Int
        val from2: Int
        val vFrom: Int
        val vTo: Int

        when(corner) {
            StatCorner.TOP_LEFT -> {
                from1 = ConstraintSet.TOP
                from2 = ConstraintSet.LEFT
                vFrom = ConstraintSet.LEFT
                vTo = ConstraintSet.RIGHT
            }
            StatCorner.TOP_RIGHT -> {
                from1 = ConstraintSet.TOP
                from2 = ConstraintSet.RIGHT
                vFrom = ConstraintSet.RIGHT
                vTo = ConstraintSet.LEFT
            }
            StatCorner.BOTTOM_LEFT -> {
                from1 = ConstraintSet.BOTTOM
                from2 = ConstraintSet.LEFT
                vFrom = ConstraintSet.LEFT
                vTo = ConstraintSet.RIGHT
            }
            StatCorner.BOTTOM_RIGHT -> {
                from1 = ConstraintSet.BOTTOM
                from2 = ConstraintSet.RIGHT
                vFrom = ConstraintSet.RIGHT
                vTo = ConstraintSet.LEFT
            }
        }

        constraint.constrainHeight(iconView.id, 50.dpToPx)
        constraint.constrainWidth(iconView.id, 50.dpToPx)
        constraint.constrainHeight(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraint.constrainWidth(titleView.id, ConstraintSet.WRAP_CONTENT)
        constraint.constrainHeight(valueView.id, ConstraintSet.WRAP_CONTENT)
        constraint.constrainWidth(valueView.id, ConstraintSet.WRAP_CONTENT)

        constraint.connect(iconView.id, from1, ConstraintSet.PARENT_ID, from1, 8.dpToPx)
        constraint.connect(iconView.id, from2, ConstraintSet.PARENT_ID, from2, 8.dpToPx)
        constraint.connect(titleView.id, vFrom, iconView.id, vTo, 8.dpToPx)
        constraint.connect(titleView.id, ConstraintSet.TOP, iconView.id, ConstraintSet.TOP)
        constraint.connect(valueView.id, ConstraintSet.TOP, titleView.id, ConstraintSet.BOTTOM)
        constraint.connect(valueView.id, vFrom, titleView.id, vFrom)

        constraint.applyTo(root)
    }*/

    override fun onStart() {
        super.onStart()
        parallaxImageView.registerSensorManager()
    }

    override fun onStop() {
        super.onStop()
        parallaxImageView.unregisterSensorManager()
    }

    companion object {
        fun newInstance() = AlphaBaseFragment()
    }
}

enum class StatCorner {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT
}
