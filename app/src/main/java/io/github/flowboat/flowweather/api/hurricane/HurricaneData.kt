package io.github.flowboat.flowweather.api.hurricane

import org.threeten.bp.ZonedDateTime

interface HurricaneData {
    val location: Int

    val issueDate: ZonedDateTime
}