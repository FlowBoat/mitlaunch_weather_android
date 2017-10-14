package io.github.flowboat.flowweather.ui.home

import android.hardware.Sensor
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.api.ApiManager
import io.github.flowboat.flowweather.api.model.sensor.DeviceReport
import io.github.flowboat.flowweather.api.model.sensor.SerializableSensorValues
import io.github.flowboat.flowweather.api.model.toSerializable
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import io.reactivex.subjects.BehaviorSubject

class HomePresenter: BasePresenter<HomeFragment>() {
    private val api: ApiManager by Kodein.global.lazy.instance()
    val testSubject = BehaviorSubject.create<SensorSendStatus>()

    suspend fun sendSensorData(sensorData: Map<Sensor, FloatArray>) {
        fun status(message: String) {
            testSubject.onNext(SensorSendStatus.Active(message))
        }

        val sensorMap = sensorData.map { (s, v) ->
            val serSensor = s.toSerializable()
            val serValue = SerializableSensorValues(v.asList())

            Pair(serSensor, serValue)
        }.toMap()

        val report = DeviceReport(sensorMap)
        api.sendReport(report)
        testSubject.onNext(SensorSendStatus.Inactive())
    }
}

sealed class SensorSendStatus {
    class Active(val status: String): SensorSendStatus()
    class Inactive: SensorSendStatus()
}