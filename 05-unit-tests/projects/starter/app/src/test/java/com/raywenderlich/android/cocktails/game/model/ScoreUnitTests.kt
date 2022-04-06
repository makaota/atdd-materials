package com.raywenderlich.android.cocktails.game.model

import com.raywenderlich.android.cocktails.game.Game
import com.raywenderlich.android.cocktails.game.Score
import org.junit.Assert
import org.junit.Test

class ScoreUnitTests {

    @Test
    fun whenIncrementingScore_shouldIncrementCurrentScore(){

        val score = Score()

        score.increment()

        Assert.assertEquals(1,score.current)

        Assert.assertEquals("Current score should have been 1",1,score.current)

    }

    @Test
    fun whenIncrementingScore_aboveHighScore_shouldAlsoIncrementHighScore(){

        val score = Score()

        score.increment()

        Assert.assertEquals(1, score.highest)
    }

    @Test
    fun whenIncrementingScore_belowHighScore_shouldNotIncrementHighScore(){

        val score = Score(10)

        score.increment()

        Assert.assertEquals(10,score.highest)
    }
}