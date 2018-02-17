package io.github.flowboat.flowweather.wox

object WoxDB {
    private val WEATHER_CLASS_MAIN = "main"

    val anyDb = mutableMapOf<Int, List<String>>()

    private val weatherDb = mutableListOf<WoxWeatherCombo>()

    val NAME = registerPlaceholder("name")

    private val GREETER = any("Hello", "Hey", "Yo", "Hi", "Good day")

    private fun registerPlaceholder(placeholder: String)
            = "%$placeholder%".toUpperCase()

    private fun any(vararg variations: String): String {
        val key = anyDb.maxBy { it.key }?.key ?: 0
        anyDb[key] = variations.toList()
        return anyKey(key)
    }

    fun anyKey(id: Int) = "%_ANY-$id%"

    val CURRENT_DAY_MESSAGE_PF = listOf(
            "$GREETER $NAME, let's take a look at today's weather:",
            "Here is today's weather report:",
            "$GREETER $NAME, here is today's weather report:",
            "$GREETER $NAME, here is what's going on with today's weather:",
            "Here is your personally prepared weather report:",
            "$GREETER $NAME, care to take a look at today's weather?",
            "$GREETER $NAME, here is a fresh new weather report, hot off the press!",
            "I've just prepared a new weather report, $NAME:",
            "Care to take a look at today's weather, $NAME?",
            "Here is how today's weather is looking:"
    )

    private fun registerWeatherStatement(before1: WoxWeatherState,
                                 before2: WoxWeatherState,
                                 current: WoxWeatherState,
                                 after1: WoxWeatherState,
                                 after2: WoxWeatherState,
                                 clazz: String,
                                 vararg messages: String) {
        weatherDb += WoxWeatherCombo(before1,
                before2,
                current,
                after1,
                after2,
                clazz,
                messages.toList())
    }

    init {
        registerWeatherStatement(
                WoxWeatherState.RAINY,
                WoxWeatherState.RAINY,
                WoxWeatherState.SUNNY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "Finally, some sun! After all that rain, the sun has finally come out today!",
                "Skies are all clear today! We are finally out of the rainy weather!",
                "Ready for some sun? You'll be getting a ton of it today!",
                "It's sunny today. A welcome change to the continuous rainy weather we've been having...",
                "At last we get some sun!"
        )
        registerWeatherStatement(
                WoxWeatherState.SNOWY,
                WoxWeatherState.SNOWY,
                WoxWeatherState.SNOWY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "Finally, some sun! After all that snow, the sun has finally come out today!",
                "Skies are all clear today! We are finally out of the snowy weather!",
                "Ready for some sun? You'll be getting a ton of it today!",
                "It's sunny today. A welcome change to the continuous snowy weather we've been having...",
                "At last we get some sun!"
        )
        registerWeatherStatement(
                WoxWeatherState.SUNNY,
                WoxWeatherState.SUNNY,
                WoxWeatherState.SUNNY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "Sun, sun, sun. Looks like it will be sunny again today!",
                "It's sunny AGAIN today!",
                "Expect it to be sunny again today",
                "Expect sun again today",
                "Clear skies today, can't have enough sun right?"
        )
        registerWeatherStatement(
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WoxWeatherState.SUNNY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "It's sunny today",
                "Expect plenty of sun today",
                "It's looking pretty sunny today",
                "Clear skies, it'll be sunny today!"
        )
        registerWeatherStatement(
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WoxWeatherState.RAINY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "It's rainy today",
                "Expect plenty of rain today",
                "It's looks like it will rain today",
                "It's going to rain, dress accordingly"
        )
        registerWeatherStatement(
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WoxWeatherState.SNOWY,
                WoxWeatherState.ANY,
                WoxWeatherState.ANY,
                WEATHER_CLASS_MAIN,
                "It's snowy today",
                "Expect plenty of snow today",
                "It's looks like it will snow today",
                "It's snowy today, dress accordingly",
                "Look forward to some snowfall today, have a safe commute!"
        )
    }

    fun findRandomExcluding(list: List<String>, vararg excluded: String)
            = list.shuffled().find { item ->
        !excluded.any {
            item.contains(it)
        }
    }
}