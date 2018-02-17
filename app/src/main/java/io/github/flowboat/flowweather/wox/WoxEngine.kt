package io.github.flowboat.flowweather.wox

import io.github.flowboat.flowweather.ui.datawindow.VolatileDW
import io.github.flowboat.flowweather.util.random

class WoxEngine {
    fun run(days: List<VolatileDW>,
            currentDayIndex: Int,
            pContext: WoxPredictionContext): String {
        val currentDay = days[currentDayIndex]

        val built = StringBuilder()
        if(pContext.relativeContext == WoxPredictionRelativeContext.TODAY)
            built.append(buildCurrentDayMessage(pContext))

        return built.toString()
    }

    fun processMessage(pContext: WoxPredictionContext, message: String): String {
        var result = message
        while(true) {
            val oldResult = result

            //Insert names
            pContext.firstName?.let {
                result = result.replace(WoxDB.NAME, it)
            }

            //Insert greeter
            WoxDB.anyDb.forEach { (k, v) ->
                result = result.replace(WoxDB.anyKey(k), v.random()!!)
            }

            if(oldResult == result)
                break //No changes, done
        }
        return result
    }

    fun buildCurrentDayMessage(pContext: WoxPredictionContext): String {
        val list = if (pContext.pointForm)
            WoxDB.CURRENT_DAY_MESSAGE_PF
        else
            TODO()
        val result = if(pContext.firstName == null)
            WoxDB.findRandomExcluding(list, WoxDB.NAME)
        else
            list.random()
        return processMessage(pContext, result!!)
    }
}
