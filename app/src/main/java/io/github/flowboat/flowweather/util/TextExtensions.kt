package io.github.flowboat.flowweather.util

import android.os.Parcel
import android.text.TextUtils

fun CharSequence.cloneWithSpans(): CharSequence {
    val parcel = Parcel.obtain()
    TextUtils.writeToParcel(this, parcel, 0)
    parcel.setDataPosition(0)
    val out = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel)
    parcel.recycle()
    return out
}