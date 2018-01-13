package io.github.flowboat.flowweather.data.bridge

class HeaderPacket: Packet {
    var version: Int = -1
    
    var deviceId: Int = -1
    
    var deviceTime: Long = -1
    
    var error: Int = -1
    
    var dataCount: Int = -1
    
    companion object {
        val HEADER = arrayOf(
                0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF, 0x00, 0xFF
        ).packBytes()
        
        val LENGTH = 28
    }
}

