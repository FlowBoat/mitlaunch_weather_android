package io.github.flowboat.flowweather.data.quiz

import android.os.Parcelable
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quiz(val questions: List<QuizQuestion>): Parcelable {

    val stringSet get() = questions.map(QuizQuestion::name).toSet()

    companion object {
        private val quizManager: QuizManager by Kodein.global.lazy.instance()

        fun fromStringSet(set: Set<String>): Quiz {
            val newQuestions = set.map { savedQuestion ->
                quizManager.quizQuestions.find {
                    it.name == savedQuestion
                } ?: throw IllegalArgumentException("Missing quiz questions!")
            }

            return Quiz(newQuestions)
        }
    }
}