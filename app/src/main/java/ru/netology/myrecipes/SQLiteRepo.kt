package ru.netology.myrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.netology.myrecipes.bd.*

class SQLiteRepo(
    val recipeActions: RecipesActions
): RecipeRepo {
    override var data = recipeActions.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun like(rId: Long) {
        recipeActions.addToFavorites(rId)
    }

    override fun delete(rId: Long) {
        recipeActions.removeById(rId)

    }

    override fun save(recipe: Recipe): RecipeEntity {
        if (recipe.id == RecipeRepo.NEWID) recipeActions.insert(recipe.toEntity())
        else recipeActions.updateContentById(recipe.id, recipe.author, recipe.name,recipe.category,recipe.imageUrl)
        return recipe.toEntity()
    }

     override fun getFavorites(): LiveData<List<Recipe>> =
         recipeActions.getFavorites().map{
             it.map {
                 it.toModel()
             }
         }


     override fun getFiltered(category:String?): LiveData<List<Recipe?>> =
         recipeActions.selectOneCategory(category).map{
             it.map {
                 it.toModel()
             }
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

    override fun insertStep(step: Step){
        recipeActions.insertStep(step.toStepEntity())
    }


}