package com.raywenderlich.android.cocktails.game.factory

import com.raywenderlich.android.cocktails.game.Game
import javax.security.auth.callback.Callback

interface CocktailsGameFactory {

    fun buildGame(callback: Callback)

        interface Callback{
            fun onSuccess(game: Game)
            fun onError()
        }
}