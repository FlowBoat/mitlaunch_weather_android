package io.github.flowboat.flowweather.api.http

import io.github.flowboat.flowweather.util.CharsetConstants
import kotlinx.serialization.json.JSON

class HttpApiSerializer {
    val charset = CharsetConstants.UTF_8.charset

    inline fun <reified T : Any> serialize(obj: T): ByteArray =
            JSON.stringify(obj).toByteArray(charset)

    inline fun <reified T : Any> parse(data: ByteArray): T =
            JSON.parse(data.toString(charset))
}