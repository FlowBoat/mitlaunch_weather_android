package io.github.flowboat.flowweather.ui.datawindow.day

import android.view.View
import android.view.ViewGroup
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent
import io.github.flowboat.flowweather.util.inflate
import io.github.flowboat.flowweather.widget.RecyclerViewPagerAdapter

class DWDayAdapter(val data: List<DayDataComponent>) : RecyclerViewPagerAdapter() {
    override fun createView(container: ViewGroup): View {
        val view = container.inflate(R.layout.item_dw_day) as DWDayView
        view.onCreate()
        return view
    }

    override fun bindView(view: View, position: Int) {
        val entry = data[position]
        (view as DWDayView).onBind(entry)
    }

    override fun getCount() = data.size
}