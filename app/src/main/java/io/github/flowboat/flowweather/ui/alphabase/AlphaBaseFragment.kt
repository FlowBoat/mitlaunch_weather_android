package io.github.flowboat.flowweather.ui.alphabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.ui.bridgeui.BridgeUIPresenter
import nucleus5.factory.RequiresPresenter

/**
 * Created by nulldev on 2/10/18.
 */

@RequiresPresenter(BridgeUIPresenter::class)
class AlphaBaseFragment : BaseRxFragment<AlphaBasePresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_alphabase, container, false)!!

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setToolbarTitle("AlphaBase Test Viewer")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance() = AlphaBaseFragment()
    }
}
