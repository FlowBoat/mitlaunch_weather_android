package io.github.flowboat.flowweather.ui.unipager

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_up_wrapper.*

class UPHolder(
        val view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: UPItem) {
        up_wrapper.removeAllViews()
        up_wrapper.addView(item.getView(up_wrapper))
    }
}
