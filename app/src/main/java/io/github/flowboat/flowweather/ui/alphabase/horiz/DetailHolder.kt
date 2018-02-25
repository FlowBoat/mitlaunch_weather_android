package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.graphics.Color
import android.view.View
import com.mikepenz.iconics.IconicsDrawable
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_detail.*

class DetailHolder(
        val view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: DetailItem) {
        detail_icon.icon = IconicsDrawable(view.context, item.detailType.icon).color(Color.WHITE)
        detail_text.text = view.context.getString(item.detailType.text, item.value)
    }
}
