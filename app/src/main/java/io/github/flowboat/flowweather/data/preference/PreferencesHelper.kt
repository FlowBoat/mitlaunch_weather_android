package io.github.flowboat.flowweather.data.preference

/**
 * Async preference API
 *
 * @author inorichi
 * @author nulldev
 */

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.Preference
import com.f2prateek.rx.preferences2.RxSharedPreferences

fun <T> Preference<T>.getOrDefault(): T = get() ?: defaultValue()!!

fun Preference<Boolean>.invert(): Boolean = getOrDefault().let { set(!it); !it }

class PreferencesHelper(val context: Context) {

    val keys = PreferenceKeys(context)

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val rxPrefs = RxSharedPreferences.create(prefs)

}
