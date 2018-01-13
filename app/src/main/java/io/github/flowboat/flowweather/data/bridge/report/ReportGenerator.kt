package io.github.flowboat.flowweather.data.bridge.report

import io.github.flowboat.flowweather.data.bridge.DataPacket
import io.github.flowboat.flowweather.data.bridge.HeaderPacket
import io.github.flowboat.flowweather.data.bridge.Packet

class ReportGenerator {
    fun gen(recvTime: Long, data: List<Packet>): Report {
        val headers = data.filterIsInstance<HeaderPacket>()
        
        if(headers.isEmpty())
            throw IllegalArgumentException("No headers found!")
        
        fun parseCombo(header: HeaderPacket): Report {
            val thisHeaderIndex = data.indexOf(header)
            val nextHeader = headers.getOrNull(headers.indexOf(header) + 1)
            val nextHeaderIndex = nextHeader?.let { data.indexOf(it) }
            
            val report = Report().apply {
                reportTime = recvTime
                protocolVersion = header.version
                deviceId = header.deviceId
                StationError.values().forEach {
                    if(it.hasMask(header.error))
                        errors.add(it)
                }
            }
            
            //Get data between this header and next header
            val thisData = data.slice(thisHeaderIndex + 1
                    until (nextHeaderIndex ?: data.size))
            
            val timeOffset = recvTime - header.deviceTime
            
            //Parse data
            thisData.forEach {
                if(it !is DataPacket)
                    throw IllegalArgumentException("Unknown data packet detected!")
                
                val entry = ReportEntry().apply {
                    collectionTime = timeOffset + it.time
                    
                    temp = -90f + 75f/32768f * it.rawTemp
                    
                    pressure = 15f/8192f * it.rawPressure
                    
                    humidity = 25f/16384f * it.rawHumidity
                    
                    windSpeed = 1f/128f * it.rawWindSpeed
                }
                
                if(!report.entries.any { it.collectionTime == entry.collectionTime })
                    report.entries.add(entry)
            }
            
            return report
        }
        
        val combos = headers.map { parseCombo(it) }
        
        return combos.maxBy { it.entries.size } ?: combos.first()
    }
}

