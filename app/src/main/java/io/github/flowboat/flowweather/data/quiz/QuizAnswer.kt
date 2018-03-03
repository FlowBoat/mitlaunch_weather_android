package io.github.flowboat.flowweather.data.quiz

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizAnswer(val name: String,
                      val correct: Boolean): Parcelable
