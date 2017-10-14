package io.github.flowboat.flowweather.api.http.util

import io.github.flowboat.flowweather.api.http.HttpApiProvider
import io.github.flowboat.flowweather.util.MimeType
import okhttp3.RequestBody

suspend inline fun <reified T : Any> T.postTo(api: HttpApiProvider, url: String)
        = api.postRequestBodyTo(RequestBody.create(MimeType.BINARY.type, api.serializer.serialize(this)), url)
