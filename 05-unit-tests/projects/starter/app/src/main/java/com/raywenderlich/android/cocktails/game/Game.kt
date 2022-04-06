package com.raywenderlich.android.cocktails.game

import com.raywenderlich.android.cocktails.model.Question

open class Game(private val questions: List<Question>, val score: Score = Score(0)) {

    private var questionIndex = -1

    fun nextQuestion(): Question?{
        if (questionIndex + 1 < questions.size){
            questionIndex++
            return questions[questionIndex]
        }
        return null
    }

    fun answer(question: Question, option: String){

       val result = question.answer(option)
        if (result) {
            score.increment()
        }
    }


}