package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.network.repository.CocktailsRepository
import com.raywenderlich.android.cocktails.common.network.repository.RepositoryCallback
import com.raywenderlich.android.cocktails.game.Game
import com.raywenderlich.android.cocktails.game.Score
import com.raywenderlich.android.cocktails.model.Question

class CocktailsGameFactoryImpl(private val repository: CocktailsRepository) : CocktailsGameFactory {

    override fun buildGame(callback: CocktailsGameFactory.Callback) {
        repository.getAlcoholic(
            object : RepositoryCallback<List<Cocktail>, String>{
                override fun onSuccess(cocktailList: List<Cocktail>) {
                    val questions = buildQuestions(cocktailList)
                    val score = Score(repository.getHighScore())
                    val game = Game(questions,score)
                    callback.onSuccess(game)
                }

                override fun onError(e: String) {
                    callback.onError()
                }

            })
    }

    private fun buildQuestions(cocktailList: List<Cocktail>) = cocktailList.map{ cocktail->
        val otherCocktail = cocktailList.shuffled().first(){it != cocktail}
        Question(cocktail.strDrink,
            otherCocktail.strDrink,
            cocktail.strDrinkThumb)

    }

}