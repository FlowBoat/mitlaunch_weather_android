package io.github.flowboat.flowweather.ui.unipager

import android.view.View
import android.view.ViewManager
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_up_wrapper.*

class UPHolder(
        val view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: UPItem) {
        up_wrapper.removeAllViews()
        val newView = item.getView(up_wrapper)
        (newView.parent as? ViewManager)?.removeView(newView)
        up_wrapper.addView(newView)
    }
}
