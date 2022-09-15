package ru.netology.myrecipes.bd

import android.database.Cursor
import ru.netology.myrecipes.Recipe
import ru.netology.myrecipes.RecipesAdapter

fun RecipeEntity.toModel() = Recipe(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite
    )

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite
)