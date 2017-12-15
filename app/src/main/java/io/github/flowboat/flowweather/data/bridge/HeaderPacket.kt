package io.github.flowboat.flowweather.data.bridge

class HeaderPacket: Packet {
    var version: Short = -1
    
    var deviceId: Int = -1
    
    var deviceTime: Long = -1
    
    var error: Short = -1
    
    companion object {
        val HEADER = arrayOf(
                0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF
        ).toIntArray().packBytes()
        
        val LENGTH = 24
    }
}

