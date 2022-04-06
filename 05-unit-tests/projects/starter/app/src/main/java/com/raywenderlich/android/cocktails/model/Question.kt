package com.raywenderlich.android.cocktails.model

import com.raywenderlich.android.cocktails.common.network.Cocktail
import java.lang.IllegalArgumentException

open class Question(val correctOption: String,
                    val incorrectOption: String,
                    val imageUrl: String? = null) {

    var answeredOption: String? = null
    private set

    val isAnsweredCorrectly: Boolean
    get() = correctOption == answeredOption

    fun answer(option: String) : Boolean{

        if (option != correctOption && option != incorrectOption){
            throw IllegalArgumentException("Not a valid option")
        }
        answeredOption = option

        return isAnsweredCorrectly
    }


}