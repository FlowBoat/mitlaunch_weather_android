package io.github.flowboat.flowweather.ui.base.holder

import android.view.View
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.extensions.LayoutContainer

/**
 * @author inorichi
 */
abstract class BaseFlexibleViewHolder(view: View,
                                      adapter: FlexibleAdapter<*>,
                                      stickyHeader: Boolean = false) :
        FlexibleViewHolder(view, adapter, stickyHeader), LayoutContainer {

    override val containerView: View?
        get() = itemView
}