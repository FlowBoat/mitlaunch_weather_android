package io.github.flowboat.flowweather.util

import android.widget.LinearLayout

/**
 * [LinearLayout] extensions.
 *
 * @author nulldev
 */

/**
 * Reverse the order of elements.
 */
fun LinearLayout.reverse() =
    this.iterable().reversed().apply {
        this@reverse.removeAllViews()
        this@reverse += this
    }