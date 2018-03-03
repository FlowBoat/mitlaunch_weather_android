package io.github.flowboat.flowweather.ui.quiz

import android.os.Bundle
import android.view.View
import com.stepstone.stepper.StepperLayout
import com.stepstone.stepper.VerificationError
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.data.quiz.QuizAnswer
import io.github.flowboat.flowweather.data.quiz.QuizQuestion
import io.github.flowboat.flowweather.ui.base.activity.BaseRxActivity
import kotlinx.android.synthetic.main.activity_quiz.*
import kotlinx.android.synthetic.main.toolbar.*
import nucleus5.factory.RequiresPresenter

@RequiresPresenter(QuizPresenter::class)
class QuizActivity : BaseRxActivity<QuizPresenter>(), StepperLayout.StepperListener {

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)

        setContentView(R.layout.activity_quiz)

        setupToolbar(toolbar)

        stepperLayout.adapter = QuizAdapter(this, supportFragmentManager, presenter.quiz)
        stepperLayout.setListener(this)
        stepperLayout.currentStepPosition = presenter.lastStepPosition
    }

    fun updateChoice(question: QuizQuestion, answer: QuizAnswer?) {
        presenter.currentQuizStatus[question] = answer
    }

    fun getChoice(question: QuizQuestion)
        = presenter.currentQuizStatus[question]

    fun getChoices() = presenter.currentQuizStatus.toMap()

    override fun onStepSelected(newStepPosition: Int) {
        presenter.lastStepPosition = newStepPosition
    }

    override fun onError(verificationError: VerificationError?) {
    }

    override fun onReturn() {
    }

    override fun onCompleted(completeButton: View?) {
        finish()
    }
}