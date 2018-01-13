package io.github.flowboat.flowweather.api.model

import io.github.flowboat.flowweather.data.bridge.report.Report

fun Report.toSerializable()
        = DeviceReport(deviceId!!.toLong(), reportTime!!, entries.map {
    SerializableReportEntry(it.collectionTime!!,
            it.temp!!.toDouble(),
            it.pressure!!.toDouble(),
            it.humidity!!.toDouble(),
            it.windSpeed!!.toDouble())
})
