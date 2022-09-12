package ru.netology.myrecipes.bd

import ru.netology.myrecipes.Recipe

interface RecipesActions {
    fun getAll(): List<Recipe>
    fun save(recipe: Recipe): Recipe
    fun addToFavorites(id:Long)
    fun remove(id:Long)
}