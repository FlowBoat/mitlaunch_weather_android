package io.github.flowboat.flowweather.api.model.sensor

import kotlinx.serialization.Serializable

@Serializable
data class DeviceReport(val sensorMap: Map<String, List<SensorDataSnapshot>>)

