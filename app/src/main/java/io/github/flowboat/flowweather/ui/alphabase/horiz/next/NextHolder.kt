package io.github.flowboat.flowweather.ui.alphabase.horiz.next

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_next.*

class NextHolder(
        val view: View,
        val adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: NextItem) {
        high.text = "${item.high}°C"
        low.text = "${item.low}°C"

        details.layoutManager = LinearLayoutManager(view.context)

        details.adapter = FlexibleAdapter(item.details)

        val width = adapter.recyclerView.width / 2

        next_root.layoutParams = ViewGroup.LayoutParams(width,
                ViewGroup.LayoutParams.MATCH_PARENT)
    }
}
