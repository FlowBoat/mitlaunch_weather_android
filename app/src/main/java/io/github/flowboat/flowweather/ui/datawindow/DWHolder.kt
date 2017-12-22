package io.github.flowboat.flowweather.ui.datawindow

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.base.holder.BaseFlexibleViewHolder
import kotlinx.android.synthetic.main.item_dw.*
import org.threeten.bp.format.DateTimeFormatter

class DWHolder(
        view: View,
        adapter: FlexibleAdapter<*>
) : BaseFlexibleViewHolder(view, adapter) {
    fun onSetValues(item: VolatileDW) {
        item.day?.let {
            date_text.text = it.format(DATE_FORMAT)
        }

        item.precip?.let {
            precip_text.text = it.displayName
            icon.text = "{" + it.icon + "}"
        }

        item.high?.let {
            high_text.text = "$it°C"
        }

        item.low?.let {
            low_text.text = "$it°C"
        }
    }

    companion object {
        val DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE, MMM. d")!!
    }
}
