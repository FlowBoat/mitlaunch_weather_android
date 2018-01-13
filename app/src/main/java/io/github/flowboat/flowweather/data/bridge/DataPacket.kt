package io.github.flowboat.flowweather.data.bridge

/**
 * Created by nulldev on 12/14/17.
 */
class DataPacket: Packet {
    var time: Long = 0
    
    var rawTemp: Int = 0
    
    var rawPressure: Int = 0
    
    var rawHumidity: Int = 0
    
    var rawWindSpeed: Int = 0
    
    companion object {
        val HEADER = arrayOf(
                0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF
        ).packBytes()
        
        val LENGTH = 16
    }
}