package io.github.flowboat.flowweather.ui.datawindow.day

import android.os.Bundle
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.activity.BaseRxActivity
import kotlinx.android.synthetic.main.toolbar.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(DWDayPresenter::class)
class DWDayActivity : BaseRxActivity<DWDayPresenter>() {
    override fun onCreate(savedState: Bundle?) {
        setAppTheme()
        super.onCreate(savedState)

        setContentView(R.layout.activity_dw_day)

        // Handle Toolbar
        setupToolbar(toolbar)
    }
}