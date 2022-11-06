package ru.netology.myrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.myrecipes.bd.RecipeEntity

data class Recipe(
        val author: String,
        val name: String,
        val category: String,
        var id: Long = 0L,
        var isFavorite: Boolean = false,
        val imageUrl: String = "",
        var steps: MutableLiveData<MutableList<Step>> = MutableLiveData<MutableList<Step>>()
    //var stepsIds: Array<Long> = emptyArray()
    ){


}

