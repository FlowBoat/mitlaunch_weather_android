package io.github.flowboat.flowweather.ui.alphabase.horiz.next

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.alphabase.horiz.DetailItem

data class NextItem(val high: Int,
                    val low: Int,
                    val details: List<DetailItem>) : AbstractFlexibleItem<NextHolder>() {
    override fun getLayoutRes() = R.layout.item_next

    override fun bindViewHolder(adapter: FlexibleAdapter<out IFlexible<*>>,
                                holder: NextHolder,
                                position: Int,
                                payloads: MutableList<Any>?) {
        holder.onSetValues(this)
    }

    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
            = NextHolder(view, adapter)
}