package io.github.flowboat.flowweather.ui.adebug

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.uber.autodispose.kotlin.autoDisposeWith
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.util.toast
import io.github.flowboat.flowweather.util.untilDestroyed
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_adebug.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(ADebugPresenter::class)
class ADebugFragment: BaseRxFragment<ADebugPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_adebug, container, false)!!
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        //TODO strings.xml
        setToolbarTitle("Arduino debug menu")
        
        start_btn.setOnClickListener {
            try {
                presenter.startDebug(context)
            } catch(e: Exception) {
                context.toast("Something broke: " + e.message, Toast.LENGTH_SHORT)
            }
        }
        
        stop_btn.setOnClickListener {
            try {
                presenter.endDebug()
            } catch(e: Exception) {}
        }
        
        presenter.subject
                .observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(untilDestroyed()).subscribe {
            it.forEach {
                output.append(Integer.toBinaryString(it.toInt() and 0xFF))
            }
        }
    }
    
    override fun onStop() {
        super.onStop()
        presenter.endDebug()
    }
    
    companion object {
        fun newInstance() = ADebugFragment()
    }
}
