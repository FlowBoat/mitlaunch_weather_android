package io.github.flowboat.flowweather.util

import android.os.Build
import android.view.View
import kotlinx.android.extensions.LayoutContainer
import java.util.concurrent.atomic.AtomicInteger

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.setRandomId() {
    id = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
        generateViewId()
    } else {
        View.generateViewId()
    }
}

private val sNextGeneratedId = AtomicInteger(1)

/**
 * Generate a value suitable for use in [.setId].
 * This value will not collide with ID values generated at build time by aapt for R.id.
 *
 * @return a generated ID value
 */
fun generateViewId(): Int {
    while (true) {
        val result = sNextGeneratedId.get()
        // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
        var newValue = result + 1
        if (newValue > 0x00FFFFFF) newValue = 1 // Roll over to 1, not 0.
        if (sNextGeneratedId.compareAndSet(result, newValue)) {
            return result
        }
    }
}

fun View.container(): LayoutContainer {
    return object : LayoutContainer {
        override val containerView = this@container
    }
}