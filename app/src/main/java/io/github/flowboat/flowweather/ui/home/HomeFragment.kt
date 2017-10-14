package io.github.flowboat.flowweather.ui.home

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uber.autodispose.kotlin.autoDisposeWith
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.util.lifecycleScope
import io.github.flowboat.flowweather.util.sensors
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.experimental.launch
import nucleus5.factory.RequiresPresenter
import java.util.concurrent.ConcurrentHashMap

@RequiresPresenter(HomePresenter::class)
class HomeFragment: BaseRxFragment<HomePresenter>(), SensorEventListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_home, container, false)!!

    private val sensorData = ConcurrentHashMap<Sensor, FloatArray>()

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //TODO strings.xml
        setToolbarTitle("Home")

        btn_send.setOnClickListener {
            sendSensorData()
        }

        presenter.testSubject
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(lifecycleScope())
                .subscribe {
                    if(it is SensorSendStatus.Inactive) {
                        progress_send.visibility = View.GONE
                        btn_send.visibility = View.VISIBLE
                    } else {
                        progress_send.visibility = View.VISIBLE
                        btn_send.visibility = View.GONE
                    }
                }
    }

    fun sendSensorData() {
        launch {
            presenter.sendSensorData(sensorData)
        }
    }

    override fun onStart() {
        super.onStart()

        context.sensors.getSensorList(Sensor.TYPE_ALL).forEach {
            context.sensors.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onStop() {
        super.onStop()

        context.sensors.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        sensorData.put(event.sensor, event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    companion object {
        fun newInstance() = HomeFragment()
    }
}