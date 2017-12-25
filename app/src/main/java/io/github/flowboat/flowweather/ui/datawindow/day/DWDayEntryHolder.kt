package io.github.flowboat.flowweather.ui.datawindow.day

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_dw_day_entry.*

class DWDayEntryHolder(
        val view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: DWDayEntry) {
        time.text = DWDayView.HOUR_FORMAT.format(item.time)
        value.text = item.value.toString()
    }
}
