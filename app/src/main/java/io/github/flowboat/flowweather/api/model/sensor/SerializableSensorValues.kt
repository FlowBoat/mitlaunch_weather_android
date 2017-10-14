package io.github.flowboat.flowweather.api.model.sensor

import kotlinx.serialization.Serializable

@Serializable
data class SerializableSensorValues(val value: List<Float>)
