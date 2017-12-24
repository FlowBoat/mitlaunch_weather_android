package io.github.flowboat.flowweather.ui.datawindow.day

import android.os.Bundle
import android.support.design.widget.TabLayout
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.activity.BaseRxActivity
import kotlinx.android.synthetic.main.activity_dw_day.*
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

        //Configure tabs
        with(tabs) {
            tabGravity = TabLayout.GRAVITY_CENTER
            tabMode = TabLayout.MODE_SCROLLABLE
        }
        tabs.setupWithViewPager(pager)

        //Issue network request for data here
        val data = TEMP_DAY_DATA

        val adapter = DWDayAdapter(data)
        pager.adapter = adapter
    }
}