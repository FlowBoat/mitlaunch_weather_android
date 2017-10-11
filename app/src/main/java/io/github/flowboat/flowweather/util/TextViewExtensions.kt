package io.github.flowboat.flowweather.util

import android.support.design.widget.TextInputLayout
import android.widget.TextView

/**
 * Useful extensions to the Android EditText widget and TextInputLayout
 *
 * @author nulldev
 */

/**
 * Get the text directly as a [String]
 */
var TextView.value: String
    get() = text.toString()
    set(s) { text = s }

/**
 * Get the text directly as a [String]
 */
var TextInputLayout.value: String
    get() = editText!!.value
    set(s) { editText!!.value = s }