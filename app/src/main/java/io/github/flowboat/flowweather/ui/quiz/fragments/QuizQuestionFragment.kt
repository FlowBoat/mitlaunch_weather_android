package io.github.flowboat.flowweather.ui.quiz.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.afollestad.materialdialogs.MaterialDialog
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.data.quiz.QuizAnswer
import io.github.flowboat.flowweather.data.quiz.QuizQuestion
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import io.github.flowboat.flowweather.ui.quiz.QuizActivity
import io.github.flowboat.flowweather.util.setRandomId
import kotlinx.android.synthetic.main.fragment_quiz_question.*
import nucleus5.factory.RequiresPresenter


@RequiresPresenter(QuizQuestionPresenter::class)
class QuizQuestionFragment : BaseRxFragment<QuizQuestionPresenter>(), Step {
    private val question: QuizQuestion by lazy {
        arguments!!.getParcelable(QUESTION_KEY) as QuizQuestion
    }
    private val step: Int by lazy {
        arguments!!.getInt(STEP_KEY)
    }

    var lastRadio: RadioButton? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
        = inflater.inflate(R.layout.fragment_quiz_question, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        qIndex.text = step.toString()
        qText.text = question.name

        val qIdMap = mutableMapOf<Int, QuizAnswer>()

        question.answers.forEach {
            val radio = RadioButton(context)

            radio.text = it.name
            radio.textSize = 20f
            radio.setRandomId()
            qIdMap[radio.id] = it

            choices.addView(radio)

            lastRadio = radio
        }

        val curChoice = (activity as QuizActivity).getChoice(question)
        choices.check(qIdMap.toList().find {
            it.second == curChoice
        }?.first ?: -1)

        choices.setOnCheckedChangeListener { _, checkedId ->
            (activity as QuizActivity).updateChoice(question, qIdMap[checkedId])
        }
    }

    override fun onSelected() {
    }

    override fun verifyStep(): VerificationError? {
        //Ensure at least one choice selected
        if(choices.checkedRadioButtonId == -1) {
            return VerificationError("No answer selected")
        }

        return null
    }

    override fun onError(error: VerificationError) {
        //TODO Strings.xml
        MaterialDialog.Builder(context!!)
                .title("Error")
                .content("Please select one answer!")
                .positiveText(android.R.string.ok)
                .show()
    }

    companion object {
        const val QUESTION_KEY = "question"
        const val STEP_KEY = "step"
    }
}