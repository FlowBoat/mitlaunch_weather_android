package io.github.flowboat.flowweather.api.http

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import ru.gildor.coroutines.okhttp.await
import timber.log.Timber

interface HttpApiProvider {
    val baseUrl: String

    val client: OkHttpClient

    val serializer: HttpApiSerializer

    suspend fun RequestBody.postTo(url: String) {
        val call = client.newCall(Request.Builder()
                .url(baseUrl + url)
                .post(this)
                .build())

        val response = call.await()

        //TODO Maybe do something with response?
    }

    suspend fun postRequestBodyTo(requestBody: RequestBody, url: String) {
        requestBody.postTo(url)
    }
}

