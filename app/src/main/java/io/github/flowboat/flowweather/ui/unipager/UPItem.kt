package io.github.flowboat.flowweather.ui.unipager

import android.view.View
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.github.flowboat.flowweather.R

abstract class UPItem : AbstractFlexibleItem<UPHolder>() {
    abstract fun getView(parent: ViewGroup): View

    /**
     * {@inheritDoc}
     */
    override fun createViewHolder(view: View, adapter: FlexibleAdapter<*>)
            = UPHolder(view, adapter)

    /**
     * {@inheritDoc}
     */
    override fun getLayoutRes() = R.layout.item_up_wrapper

    /**
     * {@inheritDoc}
     */
    override fun bindViewHolder(adapter: FlexibleAdapter<*>,
                                holder: UPHolder,
                                position: Int,
                                payloads: List<Any>?) {
        holder.onSetValues(this)
    }

    override fun equals(other: Any?): Boolean {
        return this === other
    }
}
