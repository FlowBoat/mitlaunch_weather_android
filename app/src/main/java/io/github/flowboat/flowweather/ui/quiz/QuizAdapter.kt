package io.github.flowboat.flowweather.ui.quiz

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel
import io.github.flowboat.flowweather.data.quiz.Quiz
import io.github.flowboat.flowweather.ui.quiz.fragments.QuizBeginFragment
import io.github.flowboat.flowweather.ui.quiz.fragments.QuizEndFragment
import io.github.flowboat.flowweather.ui.quiz.fragments.QuizQuestionFragment


class QuizAdapter(context: Context,
                  fm: FragmentManager,
                  val quiz: Quiz) : AbstractFragmentStepAdapter(fm, context) {
    override fun createStep(position: Int): Step {
        return when(position) {
            0 -> QuizBeginFragment()
            quiz.questions.size + 1 -> {
                QuizEndFragment()
            }
            else -> {
                val step = QuizQuestionFragment()
                val question = quiz.questions[position - 1]

                step.arguments = Bundle().apply {
                    putParcelable(QuizQuestionFragment.QUESTION_KEY, question)
                    putInt(QuizQuestionFragment.STEP_KEY, position)
                }

                step
            }
        }
    }

    override fun getCount(): Int {
        return quiz.questions.size + 2
    }

    override fun getViewModel(position: Int): StepViewModel {
        val b = StepViewModel.Builder(context)
        //TODO Strings.xml

        when(position) {
            0 -> {
                b.setTitle("Global warming quiz")
                b.setEndButtonLabel("Start")
            }
            quiz.questions.size -> {
                b.setEndButtonLabel("Finish")
            }
            quiz.questions.size + 1 -> {
                b.setTitle("Quiz complete")
                b.setBackButtonVisible(false)
                b.setEndButtonLabel("Exit")
            }
        }

        if(position != 0 && position <= quiz.questions.size) {
            b.setTitle("Quiz: Question ${position + 1} of $count")
        }

        return b.create()
    }
}

