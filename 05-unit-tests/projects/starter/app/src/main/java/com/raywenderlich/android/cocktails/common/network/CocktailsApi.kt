package com.raywenderlich.android.cocktails.common.network

import android.os.Parcelable
import com.google.gson.GsonBuilder
import com.raywenderlich.android.cocktails.BuildConfig
import kotlinx.parcelize.Parcelize
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class CocktailsContainer(val drinks: List<Cocktail>?)

@Parcelize
data class Cocktail(val idDrink: String,
                    val strDrink: String,
                    val strDrinkThumb: String): Parcelable

interface CocktailsApi {

    @GET("filter.php?a=Alcoholic")
    fun getAlcoholic(): Call<CocktailsContainer>

    @GET("search.php")
    fun getByName(@Query("s") name: String): Call<CocktailsContainer>

    @GET("lookup.php")
    fun getById(@Query("i") id: String): Call<CocktailsContainer>

    companion object Factory {
        fun create(): CocktailsApi {
            val gson = GsonBuilder().create()

            val client = OkHttpClient.Builder().apply {
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(interceptor)
                }
            }.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.thecocktaildb.com/api/json/v1/1/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(CocktailsApi::class.java)
        }
    }
}