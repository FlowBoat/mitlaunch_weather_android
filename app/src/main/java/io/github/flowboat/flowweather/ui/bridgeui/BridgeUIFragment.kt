package io.github.flowboat.flowweather.ui.bridgeui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.autodispose.kotlin.autoDisposeWith
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.util.hide
import io.github.flowboat.flowweather.util.show
import io.github.flowboat.flowweather.util.untilStopped
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_bridgeui.*
import nucleus5.factory.RequiresPresenter

/**
 * Created by nulldev on 12/15/17.
 */
@RequiresPresenter(BridgeUIPresenter::class)
class BridgeUIFragment: BaseRxFragment<BridgeUIPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_bridgeui, container, false)!!
    
    override fun onStart() {
        super.onStart()
        context?.let {
            presenter.beginUsbPoll(it)
        }

        //TODO change text to match icons
        presenter.status.observeOn(AndroidSchedulers.mainThread())
                .autoDisposeWith(untilStopped())
                .subscribe {
                    when(it) {
                        is UploadStatus.Inactive -> {
                            upload_progress.hide()
                            success_icon.hide()
                            fail_icon.hide()
                            connect_icon.show()
                            state_action.hide()
                        }
                        is UploadStatus.Processing -> {
                            upload_progress.show()
                            success_icon.hide()
                            fail_icon.hide()
                            connect_icon.hide()
                            state_action.hide()
                        }
                        is UploadStatus.Error -> {
                            upload_progress.hide()
                            success_icon.hide()
                            fail_icon.show()
                            connect_icon.hide()
                            state_action.show()
                            state_action.text = "Retry"
                        }
                        is UploadStatus.Complete -> {
                            upload_progress.hide()
                            success_icon.show()
                            fail_icon.hide()
                            connect_icon.hide()
                            state_action.show()
                            state_action.text = "Connect to another station"
                        }
                    }
                    
                    state_text.text = it.message
                }
        
        state_action.setOnClickListener {
            presenter.stopUsbPoll()
            context?.let {
                presenter.beginUsbPoll(it)
            }
        }
    }
    
    override fun onStop() {
        super.onStop()
        presenter.stopUsbPoll()
    }
    
    companion object {
        fun newInstance() = BridgeUIFragment()
    }
}