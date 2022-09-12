package ru.netology.myrecipes

import androidx.lifecycle.MutableLiveData
import ru.netology.myrecipes.bd.RecipesActions

class SQLiteRepo(
    val recipeActions: RecipesActions
): RecipeRepo {

    override val data: MutableLiveData<List<Recipe>> = MutableLiveData(recipeActions.getAll())

    override fun like(rId: Long) {
        recipeActions.addToFavorites(rId)
        recipes = recipes.map {
            if (it.id == rId) it.copy(
                isFavorite = !it.isFavorite,
            ) else it
        }
    }

    override fun delete(rId: Long) {
        recipeActions.remove(rId)
        recipes = recipes.filter { it.id != rId }
    }

    override fun save(recipe: Recipe) {
        val saved = recipeActions.save(recipe)
            if (recipe.id == RecipeRepo.NEWID) insert(saved) else update(saved)
    }


    private fun update(recipe: Recipe) {
        recipes = recipes.map {
            if (it.id == recipe.id) recipe else it
        }
    }

    private fun insert(recipe: Recipe) {
        recipes = listOf(recipe.copy()) + recipes

    }

    private var recipes = checkNotNull(data.value) { "no nullable" }


}