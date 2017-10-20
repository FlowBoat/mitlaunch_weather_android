package io.github.flowboat.flowweather.api

import io.github.flowboat.flowweather.api.http.HttpApiProvider
import io.github.flowboat.flowweather.api.http.HttpApiSerializer
import io.github.flowboat.flowweather.api.http.report.DeviceReportApi
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApiManager: HttpApiProvider,
        //Implemented APIs
        DeviceReportApi {
    override val baseUrl = "http://z1.nulldev.xyz:9999/api/v1/app"

    override val client = OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES).build()!!

    override val serializer = HttpApiSerializer()
}

