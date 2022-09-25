package ru.netology.myrecipes.bd

import ru.netology.myrecipes.Recipe
import ru.netology.myrecipes.Step

fun RecipeEntity.toModel() = Recipe(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite,
    imageUrl = imageUrl
    )

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite,
    imageUrl = imageUrl
)

fun StepEntity.toStepModel() = Step(
    id = id,
    text = text,
    imageUrl = imageUrl,
    recipeId = recipeId
)

fun Step.toStepEntity() = StepEntity(
    id = id,
    text = text,
    imageUrl = imageUrl,
    recipeId = recipeId
)