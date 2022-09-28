package ru.netology.myrecipes

data class Step(
    val text: String,
    val imageUrl: String,
    val id: Long = 0,
    var recipeId: Long = 0

)
