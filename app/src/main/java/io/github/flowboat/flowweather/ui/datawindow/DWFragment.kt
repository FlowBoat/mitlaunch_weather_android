package io.github.flowboat.flowweather.ui.datawindow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(DWPresenter::class)
class DWFragment : BaseRxFragment<DWPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_datawindow, container, false)!!
    
    companion object {
        fun newInstance() = DWFragment()
    }
}