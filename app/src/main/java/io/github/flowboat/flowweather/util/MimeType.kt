package io.github.flowboat.flowweather.util

import okhttp3.MediaType

enum class MimeType(type: String) {
    JSON("application/json; charset=utf-8"),
    BINARY("application/octet-stream");

    val type = MediaType.parse(type)
}
