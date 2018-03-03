package io.github.flowboat.flowweather.ui.quiz

import android.os.Bundle
import android.os.Parcelable
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.data.quiz.Quiz
import io.github.flowboat.flowweather.data.quiz.QuizAnswer
import io.github.flowboat.flowweather.data.quiz.QuizManager
import io.github.flowboat.flowweather.data.quiz.QuizQuestion
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import kotlinx.android.parcel.Parcelize

class QuizPresenter : BasePresenter<QuizActivity>() {
    private val qm: QuizManager by Kodein.global.lazy.instance()

    val currentQuizStatus = mutableMapOf<QuizQuestion, QuizAnswer?>()
    var lastStepPosition = 0
    lateinit var quiz: Quiz

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        if(savedState != null) {
            val os = savedState.getParcelable<State>(State.KEY)
            currentQuizStatus.clear()
            currentQuizStatus.putAll(os.currentQuizStatus)
            lastStepPosition = os.lastStepPosition
            quiz = os.quiz
        } else {
            quiz = qm.getCurrentQuiz()
        }
    }

    override fun onSave(state: Bundle) {
        super.onSave(state)

        state.putParcelable(State.KEY, State(currentQuizStatus,
                lastStepPosition,
                quiz))
    }

    @Parcelize
    data class State(val currentQuizStatus: Map<QuizQuestion, QuizAnswer?>,
                     val lastStepPosition: Int,
                     val quiz: Quiz): Parcelable {
        companion object {
            const val KEY = "state"
        }
    }
}

