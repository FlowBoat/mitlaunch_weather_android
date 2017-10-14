package io.github.flowboat.flowweather.util

import java.nio.charset.Charset

enum class CharsetConstants(set: String) {
    UTF_8("UTF-8");

    val charset = Charset.forName(set)
}