package io.github.flowboat.flowweather.api.model

import android.hardware.Sensor
import io.github.flowboat.flowweather.api.model.sensor.SerializableSensor

fun Sensor.toSerializable()
        = SerializableSensor(name, vendor, power)
