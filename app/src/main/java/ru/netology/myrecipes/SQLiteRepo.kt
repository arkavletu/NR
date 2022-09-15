package ru.netology.myrecipes

import androidx.lifecycle.map
import ru.netology.myrecipes.bd.RecipesActions
import ru.netology.myrecipes.bd.toEntity
import ru.netology.myrecipes.bd.toModel

class SQLiteRepo(
    val recipeActions: RecipesActions
): RecipeRepo {
   // private var recipes = recipeActions.getAll()//checkNotNull(data.value) { "no nullable" }
    override val data = recipeActions.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(rId: Long) {
        recipeActions.addToFavorites(rId)
    }

    override fun delete(rId: Long) {
        recipeActions.removeById(rId)

    }

    override fun save(recipe: Recipe) {
        if (recipe.id == RecipeRepo.NEWID) recipeActions.insert(recipe.toEntity())
        else recipeActions.updateContentById(recipe.id, recipe.author)

    }


//    private fun update(recipe: Recipe) {
//        recipes = recipes.map {
//            if (it.id == recipe.id) recipe else it
//        }
//    }
//
//    private fun insert(recipe: Recipe) {
//        recipes = listOf(recipe.copy()) + recipes
//
//    }




}