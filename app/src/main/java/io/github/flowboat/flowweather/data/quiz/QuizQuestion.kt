package io.github.flowboat.flowweather.data.quiz

import android.os.Parcelable
import com.github.salomonbrys.kotson.bool
import com.github.salomonbrys.kotson.obj
import com.github.salomonbrys.kotson.string
import com.google.gson.JsonObject
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizQuestion(val name: String,
                        val answers: List<QuizAnswer>): Parcelable {
    val correctAnswers get() = answers.filter(QuizAnswer::correct)

    companion object {
        fun fromJson(json: JsonObject): QuizQuestion {
            val name = json["question"].string
            val answers = json["answers"].obj.entrySet().map {
                QuizAnswer(it.key, it.value.bool)
            }

            return QuizQuestion(name, answers)
        }
    }
}

