package io.github.flowboat.flowweather.data.quiz

import android.content.Context
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.github.salomonbrys.kotson.array
import com.github.salomonbrys.kotson.obj
import com.google.gson.JsonParser
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.data.preference.PreferencesHelper

class QuizManager(private val context: Context) {
    private val jsonParser: JsonParser by Kodein.global.lazy.instance()

    private val prefs: PreferencesHelper by Kodein.global.lazy.instance()

    val quizQuestions by lazy {
        loadQuizQuestions()
    }

    private fun loadQuizQuestions(): List<QuizQuestion> {
        return context
                .resources
                .openRawResource(R.raw.quiz)
                .bufferedReader()
                .use {
                    val parsed = jsonParser.parse(it)

                    parsed.array.map {
                        QuizQuestion.fromJson(it.obj)
                    }
                }
    }

    fun getCurrentQuiz(): Quiz {
        val curQuiz = prefs.currentQuiz.get()

        if(curQuiz.isEmpty()) {
            // No current quiz, prepare new one!
            return pushQuiz()
        }

        return try {
            Quiz.fromStringSet(curQuiz)
        } catch(e: IllegalArgumentException) {
            // Missing quiz questions, prepare new quiz
            pushQuiz()
        }
    }

    private fun pushQuiz(): Quiz {
        // Generate random quiz
        val questions = quizQuestions.shuffled().take(QUESTIONS_PER_QUIZ)

        val quiz = Quiz(questions)

        // Save quiz
        prefs.currentQuiz.set(quiz.stringSet)

        return quiz
    }

    fun popQuiz() {
        prefs.currentQuiz.set(emptySet())
    }

    companion object {
        private const val QUESTIONS_PER_QUIZ = 5
    }
}