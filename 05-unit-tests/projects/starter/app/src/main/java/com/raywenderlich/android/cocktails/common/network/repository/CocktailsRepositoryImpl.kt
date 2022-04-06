package com.raywenderlich.android.cocktails.common.network.repository

import android.content.SharedPreferences
import com.raywenderlich.android.cocktails.common.network.Cocktail
import com.raywenderlich.android.cocktails.common.network.CocktailsApi
import com.raywenderlich.android.cocktails.common.network.CocktailsContainer
import com.raywenderlich.android.cocktails.game.Score
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val HIGH_SCORE_KEY = "HIGH_SCORE_KEY"

class CocktailsRepositoryImpl(
    private val api: CocktailsApi,
    private val sharedPreferences: SharedPreferences) : CocktailsRepository {

    private var getAlcoholicCall: Call<CocktailsContainer>? = null

    override fun getAlcoholic(callback: RepositoryCallback<List<Cocktail>, String>) {
        getAlcoholicCall?.cancel()
        getAlcoholicCall = api.getAlcoholic()
        getAlcoholicCall?.enqueue(wrapCallback(callback))
    }

    private fun wrapCallback(callback: RepositoryCallback<List<Cocktail>, String>) =
        object : Callback<CocktailsContainer> {
            override fun onResponse(call: Call<CocktailsContainer>?,
                                    response: Response<CocktailsContainer>?) {
                if (response != null && response.isSuccessful) {
                    val cocktailsContainer = response.body()
                    if (cocktailsContainer != null) {
                        callback.onSuccess(cocktailsContainer.drinks ?: emptyList())
                        return
                    }
                }
                callback.onError("Couldn't get cocktails")
            }

            override fun onFailure(call: Call<CocktailsContainer>?, t: Throwable?) {
                callback.onError("Couldn't get cocktails")
            }
        }

    override fun saveHighScore(score: Int) {
        val highScore = getHighScore()
        if (score > highScore) {
            val editor = sharedPreferences.edit()
            editor.putInt(HIGH_SCORE_KEY, score)
            editor.apply()
        }
    }

    override fun getHighScore(): Int {
        return sharedPreferences.getInt(HIGH_SCORE_KEY, 0)
    }
}