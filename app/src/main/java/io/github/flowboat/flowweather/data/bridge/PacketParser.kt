package io.github.flowboat.flowweather.data.bridge

import timber.log.Timber

/**
 * Created by nulldev on 12/14/17.
 */
class PacketParser {
    fun parse(bytes: ByteArray): List<Packet> {
        val cursor = ByteCursor(bytes)
        
        var headerIndexes = mutableListOf<Int>()
        var dataIndexes = mutableListOf<Int>()
        
        //Find all header and data indexes
        while(!cursor.atEnd()) {
            if(cursor.checkEqual(*HeaderPacket.HEADER))
                headerIndexes.add(cursor.index)
            
            if(cursor.checkEqual(*DataPacket.HEADER))
                dataIndexes.add(cursor.index)
            
            cursor.next()
        }
        
        //Trim invalid packets
        fun nextIndex(oldIndex: Int): Int? {
            val possibleHeaderIndex = headerIndexes.find { it > oldIndex }
            val possibleDataIndex = dataIndexes.find { it > oldIndex }
            
            if(possibleHeaderIndex == null)
                if(possibleDataIndex != null)
                    return possibleDataIndex
            
            if(possibleDataIndex == null)
                if(possibleHeaderIndex != null)
                    return possibleHeaderIndex
    
            return if(possibleDataIndex != null && possibleHeaderIndex != null)
                Math.min(possibleHeaderIndex, possibleDataIndex)
            else null
        }
    
        //Remove all header packets that are too short
        headerIndexes = headerIndexes.filter {
            val nextIndex = nextIndex(it) ?: return@filter true
            
            nextIndex - it >= HeaderPacket.LENGTH
        }.toMutableList()
    
        //Remove all data packets that are too short
        dataIndexes = dataIndexes.filter {
            val nextIndex = nextIndex(it) ?: return@filter true
        
            nextIndex - it >= DataPacket.LENGTH
        }.toMutableList()
        
        //TODO Better error handling
        return headerIndexes.mapNotNull {
            cursor.jumpToIndex(it)
            try {
                parseHeader(cursor)
            } catch (e: Exception) {
                Timber.w(e, "An error occurred while processing a data packet!")
                null
            }
        } + dataIndexes.mapNotNull {
            cursor.jumpToIndex(it)
            try {
                parseData(cursor)
            } catch (e: Exception) {
                Timber.w(e, "An error occurred while processing a data packet!")
                null
            }
        }
    }
    
    fun parseHeader(cursor: ByteCursor) = HeaderPacket().apply {
        //Read header
        cursor.expect(*HeaderPacket.HEADER)
        
        version = cursor.nextShort()
        
        deviceId = cursor.nextInt()
        
        deviceTime = cursor.nextLong()
        
        error = cursor.nextShort()
    }
    
    fun parseData(cursor: ByteCursor) = DataPacket().apply {
        //Read header
        cursor.expect(*DataPacket.HEADER)
        
        time = cursor.nextLong()
        
        rawTemp = cursor.nextShort()
        
        rawPressure = cursor.nextShort()
        
        rawHumidity = cursor.nextShort()
        
        rawWindSpeed = cursor.nextShort()
    }
}