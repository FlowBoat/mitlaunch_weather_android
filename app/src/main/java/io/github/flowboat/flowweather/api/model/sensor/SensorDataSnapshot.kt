package io.github.flowboat.flowweather.api.model.sensor

import kotlinx.serialization.Serializable

//TODO SHARED CLASS!
@Serializable
data class SensorDataSnapshot(val sensor: SerializableSensor, val value: SerializableSensorValues)

