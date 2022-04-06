package com.raywenderlich.android.cocktails.common.network.repository

import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.game.Score


    interface CocktailsRepository {
        fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>)
        fun saveHighScore(score: Int)
        fun getHighScore(): Int
    }

    interface RepositoryCallback<T, E> {
        fun onSuccess(t: T)
        fun onError(e: E)
    }