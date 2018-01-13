package io.github.flowboat.flowweather.ui.home

import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter

class HomePresenter: BasePresenter<HomeFragment>() {
    /*
    private val api: ApiManager by Kodein.global.lazy.instance()
    val stateSubject = BehaviorSubject.create<SensorStatus>()
    val statusSubject = BehaviorSubject.create<String>()

    val currentSensorData = ConcurrentHashMap<Sensor, FloatArray>()
    val dataSnapshots = ConcurrentHashMap<ZonedDateTime, List<Pair<Sensor, FloatArray>>>()
    var lastSnapshot: ZonedDateTime? = null

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        statusSubject.onNext("Ready")
        stateSubject.onNext(SensorStatus.Inactive())
    }

    fun startRecording() {
        dataSnapshots.clear()
        takeSnapshot()
        stateSubject.onNext(SensorStatus.Recording())
    }

    fun recordData(sensor: Sensor, values: FloatArray) {
        if(stateSubject.value is SensorStatus.Recording) {
            currentSensorData[sensor] = values

            //Take next snapshot if it is time
            if(ZonedDateTime.now().minusSeconds(DATA_COLLECTION_INTERVAL).isAfter(lastSnapshot)) {
                takeSnapshot()
            }
        }
    }

    fun takeSnapshot() {
        lastSnapshot = ZonedDateTime.now()
        dataSnapshots.put(lastSnapshot!!, currentSensorData.toList())
        statusSubject.onNext("Recording: ${dataSnapshots.size} snapshot(s) taken!")
    }

    suspend fun saveRecording() {
        statusSubject.onNext("Sending report...")
        stateSubject.onNext(SensorStatus.Sending())

        val sensorMap = dataSnapshots.entries.mapIndexed { index, (s, v) ->
            statusSubject.onNext("Sending report: Assembling component ${index + 1} of ${dataSnapshots.size}")

            val time = s.toString()
            val values = v.map { SensorDataSnapshot(it.first.toSerializable(), SerializableSensorValues(it.second.toList())) }

            Pair(time, values)
        }.associate { it }

        val report = DeviceReport(sensorMap)

        statusSubject.onNext("Sending report: Transmitting report to server...")
        api.sendReport(report)

        statusSubject.onNext("Report sent!")
        stateSubject.onNext(SensorStatus.Inactive())
    }

    companion object {
        //Seconds
        private val DATA_COLLECTION_INTERVAL = 15L
    }*/
}

sealed class SensorStatus {
    class Inactive: SensorStatus()
    class Recording: SensorStatus()
    class Sending: SensorStatus()
}