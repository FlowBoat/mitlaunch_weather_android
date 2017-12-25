package io.github.flowboat.flowweather.ui.datawindow.day

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import io.github.flowboat.flowweather.R
import org.threeten.bp.ZonedDateTime

data class DWDayEntry(val time: ZonedDateTime, val value: Double): AbstractFlexibleItem<DWDayEntryHolder>() {
    override fun getLayoutRes() = R.layout.item_dw_day_entry
    
    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>,
                                holder: DWDayEntryHolder,
                                position: Int,
                                payloads: MutableList<Any>?) {
        holder.onSetValues(this)
    }
    
    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
        = DWDayEntryHolder(view, adapter)
}