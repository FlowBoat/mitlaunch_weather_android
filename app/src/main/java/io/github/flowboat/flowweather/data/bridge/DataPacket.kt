package io.github.flowboat.flowweather.data.bridge

/**
 * Created by nulldev on 12/14/17.
 */
class DataPacket: Packet {
    var time: Long = 0
    
    var rawTemp: Short = 0
    
    var rawPressure: Short = 0
    
    var rawHumidity: Short = 0
    
    var rawWindSpeed: Short = 0
    
    companion object {
        val HEADER = arrayOf(
                0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF
        ).toIntArray().packBytes()
        
        val LENGTH = 16
    }
}