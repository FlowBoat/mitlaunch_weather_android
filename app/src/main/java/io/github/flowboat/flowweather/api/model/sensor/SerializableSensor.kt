package io.github.flowboat.flowweather.api.model.sensor

import kotlinx.serialization.Serializable

@Serializable
data class SerializableSensor(val name: String,
                              val vendor: String,
                              val power: Float)

