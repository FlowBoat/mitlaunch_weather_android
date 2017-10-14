package io.github.flowboat.flowweather.api.http.report

import io.github.flowboat.flowweather.api.http.HttpApiProvider
import io.github.flowboat.flowweather.api.http.util.postTo
import io.github.flowboat.flowweather.api.model.sensor.DeviceReport

interface DeviceReportApi : HttpApiProvider {
    suspend fun sendReport(report: DeviceReport) {
        report.postTo(this, "/submit-report")
    }
}

