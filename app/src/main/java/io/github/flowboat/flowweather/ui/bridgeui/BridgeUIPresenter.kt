package io.github.flowboat.flowweather.ui.bridgeui

import android.content.Context
import android.os.Bundle
import com.physicaloid.lib.Physicaloid
import io.github.flowboat.flowweather.data.bridge.PacketParser
import io.github.flowboat.flowweather.data.bridge.packByte
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class BridgeUIPresenter: BasePresenter<BridgeUIFragment>() {
    private val usbLock = ReentrantLock()
    private val continuePolling = AtomicBoolean(false)
    private var conn: Physicaloid? = null
    val status = BehaviorSubject.createDefault<UploadStatus>(UploadStatus.Inactive())!!
    
    fun beginUsbPoll(context: Context) {
        //Check if already polling
        if(continuePolling.get())
            return
        
        thread {
            continuePolling.set(true)
            
            usbLock.lock()
            
            try {
                conn = Physicaloid(context)
                
                while (continuePolling.get()) {
                    pollUsb()
                    Thread.sleep(1000)
                }
            } catch (e: InterruptedException) {
                //Ignore
            } catch(e: Exception) {
                status.onNext(UploadStatus.Error("Serial polling failed!"))
                Timber.e(e, "Serial polling failed!")
            } finally {
                conn?.close()
            }
            
            stopUsbPoll()
            usbLock.unlock()
        }
    }
    
    fun stopUsbPoll() {
        continuePolling.set(false)
    }
    
    private fun pollUsb() {
        if(conn?.open() == true) {
            beginUpload()
        }
    }
    
    private fun beginUpload() {
        status.onNext(UploadStatus.Processing("Establishing connection with station..."))
        
        val conn = conn ?: run {
            status.onNext(UploadStatus.Error("Connection handle is null!"))
            return
        }
        
        val byteList = mutableListOf<Byte>()
        var lastCheck = -1
        val readArray = ByteArray(4096)
        
        reader@while(true) {
            val read = conn.read(readArray)
            byteList += readArray.take(read)
            
            if(read == 0) {
                for (i in byteList.lastIndex downTo lastCheck + 1) {
                    if (byteList[i] == TERM_BYTE) {
                        for (a in i - 1 downTo i - TERM_LENGTH) {
                            if (byteList[a] == TERM_BYTE) {
                                //STOP, term byte reached
                                break@reader
                            }
                        }
                    }
                }
                lastCheck = byteList.lastIndex
            } else {
                status.onNext(UploadStatus.Processing("Reading data from station: ${byteList.size} bytes read so far..."))
            }
        }
    
        status.onNext(UploadStatus.Processing("Parsing data..."))
        val parsed = PacketParser().parse(byteList.toByteArray())
        
        status.onNext(UploadStatus.Processing("Uploading data..."))
        //TODO Upload byte list
        
        status.onNext(UploadStatus.Complete())
    }
    
    companion object {
        private val TERM_BYTE = 0xFE.packByte()
        private val TERM_LENGTH = 4
    }
}

sealed class UploadStatus(val message: String) {
    class Inactive: UploadStatus("Please connect your device to a weather station")
    class Complete: UploadStatus("Upload complete, thank you!")
    class Error(message: String): UploadStatus("An error occurred during the upload: $message")
    class Processing(message: String): UploadStatus(message)
}