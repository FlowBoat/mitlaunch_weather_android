package io.github.flowboat.flowweather.ui.quiz.fragments

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.squareup.phrase.Phrase
import com.stepstone.stepper.Step
import com.stepstone.stepper.VerificationError
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.data.quiz.QuizAnswer
import io.github.flowboat.flowweather.data.quiz.QuizManager
import io.github.flowboat.flowweather.data.quiz.QuizQuestion
import io.github.flowboat.flowweather.ui.base.fragment.BaseFragment
import io.github.flowboat.flowweather.ui.quiz.QuizActivity
import io.github.flowboat.flowweather.util.cloneWithSpans
import kotlinx.android.synthetic.main.fragment_quiz_complete.*

class QuizEndFragment : BaseFragment(), Step {
    private val qm: QuizManager by Kodein.global.lazy.instance()

    var inflated = false
    var runOnInflation = false

    override fun onSelected() {
        if(inflated)
            updateData()
        else
            runOnInflation = true
    }

    fun updateData() {
        val choices = (activity as QuizActivity).getChoices()

        fun QuizQuestion.isCorrect() = choices[this]?.let {
            it in this.correctAnswers
        } ?: false

        var correct = 0

        val resString = choices.entries.mapIndexed { index, entry ->
            val isCorrect = entry.key.isCorrect()

            val resultPhrase = if(isCorrect) {
                correct++

                context!!.getText(R.string.quiz_item_result_correct)
            } else {
                context!!.getText(R.string.quiz_item_result_incorrect)
            }.cloneWithSpans()

            Phrase.from(context!!, R.string.quiz_complete_summary_item)
                    .put("number", (index + 1).toString())
                    .put("title", entry.key.name)
                    .put("user_choice", entry.value?.name ?: "")
                    .put("answers", entry.key
                            .answers
                            .filter(QuizAnswer::correct)
                            .joinToString(", ", transform = QuizAnswer::name))
                    .put("result", resultPhrase)
                    .format()
                    .cloneWithSpans()
        }.joinTo(SpannableStringBuilder(), "\n\n")

        quizDetail.text = resString

        quizTitle.text = Phrase.from(context!!, R.string.quiz_complete_title)
                .put("correct", correct.toString())
                .put("total", choices.size.toString())
                .format()

        //Done with this quiz, generate new one next time
        qm.popQuiz()
    }

    override fun verifyStep(): VerificationError? = null

    override fun onError(error: VerificationError) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_quiz_complete, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(runOnInflation) {
            updateData()
            runOnInflation = false
        }

        inflated = true
    }
}