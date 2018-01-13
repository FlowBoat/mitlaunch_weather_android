package io.github.flowboat.flowweather.ui.bridgeui

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.kizitonwose.time.Interval
import com.kizitonwose.time.milliseconds
import com.physicaloid.lib.Physicaloid
import io.github.flowboat.flowweather.api.ApiManager
import io.github.flowboat.flowweather.api.model.toSerializable
import io.github.flowboat.flowweather.data.bridge.PacketParser
import io.github.flowboat.flowweather.data.bridge.packByte
import io.github.flowboat.flowweather.data.bridge.packBytes
import io.github.flowboat.flowweather.data.bridge.report.ReportGenerator
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import io.github.flowboat.flowweather.util.value2Dec
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.experimental.runBlocking
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

class BridgeUIPresenter: BasePresenter<BridgeUIFragment>() {
    private val api: ApiManager by Kodein.global.lazy.instance()
    
    private val usbLock = ReentrantLock()
    private val continuePolling = AtomicBoolean(false)
    private var conn: Physicaloid? = null
    val status = BehaviorSubject.createDefault<UploadStatus>(UploadStatus.Inactive())!!
    
    fun beginUsbPoll(context: Context) {
        //Check if already polling
        if(continuePolling.get())
            return
        
        thread {
            status.onNext(UploadStatus.Inactive())
            continuePolling.set(true)
            
            usbLock.lock()
            
            try {
                conn = Physicaloid(context)
                conn?.setBaudrate(9600)
                
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
        val readArray = ByteArray(2000000)
        var foundFirstEnd = false
        val startMs = System.currentTimeMillis()
        
        reader@while(true) {
            val read = conn.read(readArray)
            
            if(read <= 0 && byteList.isEmpty()) {
                //Nothing read and is first read, SPAM THE BYTES!!!
                conn.write(REQUEST_DATA_BYTES_MESSAGE)
                continue@reader
            }
            
            if(!conn.isOpened) {
                stopUsbPoll()
                status.onNext(UploadStatus.Error("Data transmission interrupted!"))
                Timber.e("Data transmission interrupted!")
                return
            }
            
            byteList += readArray.take(read)
            
            if(read == 0) {
                checker@for (i in byteList.lastIndex downTo lastCheck + 1) {
                    if (byteList[i] == TERM_BYTE) {
                        var valid = true
                        
                        for (a in i - 1 downTo i - TERM_LENGTH + 1) {
                            if (byteList[a] != TERM_BYTE) {
                                valid = false
                                break
                            }
                        }
                        
                        if(valid) {
                            break@reader
                            
                            /* Used to detect two ends (not used anymore)
                            //STOP, term byte reached
                            if(foundFirstEnd) {
                                break@reader
                            } else {
                                //Wait until next set of end bytes to guarantee we received enough data
                                foundFirstEnd = true
                                break
                            }*/
                        }
                    }
                }
                lastCheck = byteList.lastIndex
            } else {
                val diff = (System.currentTimeMillis() - startMs).milliseconds
                status.onNext(UploadStatus.Processing("Reading data from station: ${byteList.size} bytes read so far (${diff.inSeconds.value2Dec}s elapsed)..."))
            }
        }
        
        status.onNext(UploadStatus.Processing("Parsing data..."))
        val parsed = PacketParser().parse(byteList.toByteArray())
        val report = ReportGenerator().gen(startMs, parsed)
    
        status.onNext(UploadStatus.Processing("Uploading data..."))
        runBlocking {
            api.sendReport(report.toSerializable())
        }
    
        status.onNext(UploadStatus.Complete((System.currentTimeMillis() - startMs).milliseconds))
        stopUsbPoll()
    }
    
    companion object {
        private val TERM_BYTE = 0xFE.packByte()
        private val TERM_LENGTH = 4
        private val REQUEST_DATA_BYTES_MESSAGE = arrayOf(0x00).packBytes()
    }
}

sealed class UploadStatus(val message: String) {
    class Inactive: UploadStatus("Please connect your device to a weather station")
    class Complete(diffMs: Interval<*>): UploadStatus("Upload complete, thank you (took ${diffMs.inSeconds.value2Dec}s)!")
    class Error(message: String): UploadStatus("An error occurred during the upload: $message")
    class Processing(message: String): UploadStatus(message)
}