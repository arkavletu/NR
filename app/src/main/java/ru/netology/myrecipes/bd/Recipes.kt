package ru.netology.myrecipes.bd

import android.database.Cursor
import ru.netology.myrecipes.Recipe
import ru.netology.myrecipes.RecipesAdapter

fun Cursor.toRecipe() = Recipe(
    id = getLong(getColumnIndexOrThrow(RecipesTable.Columns.ID.columnName)),
    name = getString(getColumnIndexOrThrow(RecipesTable.Columns.NAME.columnName)),
    author = getString(getColumnIndexOrThrow(RecipesTable.Columns.AUTHOR.columnName)),
    category = getString(getColumnIndexOrThrow(RecipesTable.Columns.CATEGORY.columnName)),
    isFavorite = getInt(getColumnIndexOrThrow(RecipesTable.Columns.ISFAVORITE.columnName)) != 0
    )