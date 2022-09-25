package ru.netology.myrecipes

import androidx.lifecycle.MutableLiveData

data class Recipe(
        val author: String,
        val name: String,
        val category: String,
        var id: Long = 0L,
        var isFavorite: Boolean = false,
        val imageUrl: String = "",

    //var stepsIds: Array<Long> = emptyArray()
    ){
    var steps: List<Step> = emptyList()

}
