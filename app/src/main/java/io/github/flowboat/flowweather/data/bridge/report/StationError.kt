package io.github.flowboat.flowweather.data.bridge.report

enum class StationError(val mask: Int) {
    LOW_BATTERY(0x8000),
    THERMAL_FAIL(0x4000),
    BAROMETER_FAIL(0x2000),
    HYGROMETER_FAIL(0x1000),
    ANEMOMETER_FAIL(0x800),
    STORAGE_FAIL(0x400),
    UNKNOWN(0x1);
    
    fun hasMask(input: Int): Boolean
            = input and mask > 0
}