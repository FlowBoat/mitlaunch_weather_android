package io.github.flowboat.flowweather.data.preference

/**
 * Async preference API
 *
 * @author inorichi
 * @author nulldev
 */

import android.content.Context
import android.preference.PreferenceManager
import com.f2prateek.rx.preferences2.RxSharedPreferences

//fun <T> Preference<T>.getOrDefault(): T = get() ?: defaultValue()!!
//
//fun Preference<Boolean>.invert(): Boolean = getOrDefault().let { set(!it); !it }

private typealias Keys = PreferenceKeys

class PreferencesHelper(val context: Context) {

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    private val rxPrefs = RxSharedPreferences.create(prefs)

    val currentQuiz = rxPrefs.getStringSet(Keys.quiz_current, emptySet())

    val locationCurrent = rxPrefs.getInteger(Keys.location_current, 6176823)
}
