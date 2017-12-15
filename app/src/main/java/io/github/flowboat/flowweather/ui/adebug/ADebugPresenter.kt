package io.github.flowboat.flowweather.ui.adebug

import android.content.Context
import com.physicaloid.lib.Physicaloid
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import kotlin.concurrent.thread

class ADebugPresenter: BasePresenter<ADebugFragment>() {
    var connection: Physicaloid? = null
    var subject = ReplaySubject.create<ByteArray>()
    var task: Thread? = null
    
    fun startDebug(context: Context) {
        synchronized(this) {
            if(connection != null) {
                throw IllegalStateException("Connection already open!")
            }
            connection = Physicaloid(context).apply {
                if (!open())
                    throw IllegalStateException("Could not open connection to Arduino device")
            }
            task = thread {
                val buf = ByteArray(256)
    
                val read = connection?.read(buf)
                if(read != null) {
                    val realBuf = buf.slice(0 until read).toByteArray()
                    subject.onNext(realBuf)
                }
            }
        }
    }
    
    fun endDebug() {
        synchronized(this) {
            connection?.close()
            task?.interrupt()
        }
    }
}