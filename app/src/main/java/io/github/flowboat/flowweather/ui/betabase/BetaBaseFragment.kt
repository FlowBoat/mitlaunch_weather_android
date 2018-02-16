package io.github.flowboat.flowweather.ui.betabase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import kotlinx.android.synthetic.main.fragment_betabase.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(BetaBasePresenter::class)
class BetaBaseFragment : BaseRxFragment<BetaBasePresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_betabase, container, false)!!

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setToolbarTitle("BetaBase Test Viewer")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupGLSL()
    }

    fun setupGLSL() {
        beta.setFragmentShader(SHADER, 1f)
    }

    companion object {
        fun newInstance() = BetaBaseFragment()
    }
}
