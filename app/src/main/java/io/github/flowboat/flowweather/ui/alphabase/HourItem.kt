package io.github.flowboat.flowweather.ui.alphabase

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import io.github.flowboat.flowweather.R

data class HourItem(val hourIndex: Int,
                    val rangeBegin: Double,
                    val rangeEnd: Double,
                    val begin: Double,
                    val end: Double,
                    val showEdge: Boolean): AbstractFlexibleItem<HourHolder>() {
    override fun getLayoutRes() = R.layout.item_hour

    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>,
                                holder: HourHolder,
                                position: Int,
                                payloads: MutableList<Any>?) {
        holder.onSetValues(this)
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
            = HourHolder(view, adapter)
}
