package io.github.flowboat.flowweather.wox

import io.github.flowboat.flowweather.ui.datawindow.VolatileDW
import org.junit.Test

class WoxEngineTest {
    @Test
    fun run() {
        val context = WoxPredictionContext()
        context.firstName = "Andy"
        context.pointForm = true
        context.relativeContext = WoxPredictionRelativeContext.TODAY

        val result = WoxEngine().run(listOf(
                VolatileDW()
        ), 0, context)
        println(result)
    }
}