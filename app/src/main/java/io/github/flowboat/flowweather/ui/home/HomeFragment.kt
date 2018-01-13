package io.github.flowboat.flowweather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(HomePresenter::class)
class HomeFragment: BaseRxFragment<HomePresenter>()/*, SensorEventListener */{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_home, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO strings.xml
        setToolbarTitle("Home")

        /*
        btn_send.setOnClickListener {
            btnSendClick()
        }

        btn_force_snapshot.setOnClickListener {
            forceSnapshot()
        }

        presenter.stateSubject
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(untilDestroyed())
                .subscribe {
                    when(it) {
                        is SensorStatus.Inactive -> {
                            btn_send.visibility = View.VISIBLE
                            btn_send.text = "Start Recording" //TODO strings.xml
                            btn_force_snapshot.visibility = View.GONE
                            progress_send.visibility = View.GONE
                        }
                        is SensorStatus.Recording -> {
                            btn_send.visibility = View.VISIBLE
                            btn_send.text = "Stop and send recording" //TODO strings.xml
                            btn_force_snapshot.visibility = View.VISIBLE
                            progress_send.visibility = View.GONE
                        }
                        is SensorStatus.Sending -> {
                            btn_send.visibility = View.GONE
                            btn_force_snapshot.visibility = View.GONE
                            progress_send.visibility = View.VISIBLE
                        }
                    }
                }

        presenter.statusSubject
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(untilDestroyed())
                .subscribe {
                    text_status.text = it
                }*/
    }

    /*
    fun btnSendClick() {
        launch {
            when(presenter.stateSubject.value ?: SensorStatus.Inactive()) {
                is SensorStatus.Inactive -> presenter.startRecording()
                is SensorStatus.Recording -> presenter.saveRecording()
                is SensorStatus.Sending -> {}
            }
        }
    }

    fun forceSnapshot() {
        presenter.takeSnapshot()
    }

    override fun onStart() {
        super.onStart()

        context?.sensors?.getSensorList(Sensor.TYPE_ALL)?.forEach {
            context?.sensors?.registerListener(this, it, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onStop() {
        super.onStop()

        context?.sensors?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        presenter.recordData(event.sensor, event.values)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    */

    companion object {
        fun newInstance() = HomeFragment()
    }
}