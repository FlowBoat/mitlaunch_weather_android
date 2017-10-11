package io.github.flowboat.flowweather.util

import android.app.Notification
import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.NotificationCompat
import android.view.LayoutInflater
import android.widget.Toast

/**
 * Useful context extensions
 *
 * @author inorichi
 * @author nulldev
 */

/**
 * Display a toast in this context.
 * @param resource the text resource.
 * @param duration the duration of the toast. Defaults to short.
 */
fun Context.toast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, resource, duration).show()
}

/**
 * Display a toast in this context.
 * @param text the text to display.
 * @param duration the duration of the toast. Defaults to short.
 */
fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text, duration).show()
}

/**
 * Helper method to create a notification.
 * @param func the function that will execute inside the builder.
 * @return a notification to be displayed or updated.
 */
inline fun Context.notification(func: NotificationCompat.Builder.() -> Unit): Notification {
    val builder = NotificationCompat.Builder(this)
    builder.func()
    return builder.build()
}

/**
 * Shortcut method to get layout inflater
 */
val Context.layoutInflater: LayoutInflater
    get() = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
