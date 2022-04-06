package com.raywenderlich.android.cocktails.game.model

import com.raywenderlich.android.cocktails.game.Game
import com.raywenderlich.android.cocktails.game.Score
import com.raywenderlich.android.cocktails.model.Question
import net.bytebuddy.asm.Advice
import org.junit.Assert
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*

class GameUnitTests {



    @Test
    fun whenGettingNextQuestion_shouldReturnIt(){
        val question1 = Question("CORRECT","INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        val nextQuestion = game.nextQuestion()

        Assert.assertSame(question1,nextQuestion)
    }

    @Test
    fun whenGettingNextQuestion_withoutMoreQuestions_shouldReturnNull(){
        val question1 = Question("CORRECT","INCORRECT")
        val questions = listOf(question1)
        val game = Game(questions)

        game.nextQuestion()
        val nextQuestion = game.nextQuestion()

        Assert.assertNull(nextQuestion)
    }


    @Test
    fun whenAnswering_shouldDelegateToQuestion(){
        val question = mock<Question>()
        val game = Game(listOf(question))

        game.answer(question, "OPTION")

        verify(question).answer(eq("OPTION"))
    }

    @Test
    fun whenAnsweringCorrectly_ShouldIncrementCurrentScore(){

        val question = mock<Question>()

        whenever(question.answer(anyString())).thenReturn(true)

        val score = mock<Score>()

        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score).increment()

    }

    @Test
    fun whenAnsweringIncorrectly_shouldNotIncrementCurrentScore(){

        val question = mock<Question>()

        whenever(question.answer(anyString())).thenReturn(false)

        val score = mock<Score>()

        val game = Game(listOf(question), score)

        game.answer(question, "OPTION")

        verify(score, never()).increment()
    }


}