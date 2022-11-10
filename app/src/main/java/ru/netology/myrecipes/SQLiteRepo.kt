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


//    override fun getStepsForRecipe(id: Long): LiveData<List<Step>> =
//        recipeActions.getStepsForRecipe(id).map { entities ->
//            entities.map { it.toStepModel() }
//        }


    override fun like(rId: Long) {
        recipeActions.addToFavorites(rId)
    }

    override fun delete(rId: Long) {
        recipeActions.removeById(rId)
        //recipeActions.removeByRecId(rId)
    }

    override fun save(recipe: Recipe): RecipeEntity {
        if (recipe.id == RecipeRepo.NEWID) recipeActions.insert(recipe.toEntity())
        else recipeActions.updateContentById(recipe.id, recipe.author, recipe.name,recipe.category,recipe.imageUrl, recipe.steps)
        return recipe.toEntity()
    }

     override fun getFavorites(): LiveData<List<Recipe>> =
         recipeActions.getFavorites().map{
             it.map {
                 it.toModel()
             }
         }


     override fun getFiltered(category:String): LiveData<List<Recipe?>> =
         recipeActions.selectOneCategory(category).map{
             it.map {
                 it.toModel()
             }
         }
    override fun getFilteredFavorites(category:String): LiveData<List<Recipe?>> =
        recipeActions.selectOneCategoryFromFavorites(category).map{
            it.map {
                it.toModel()
            }
        }

    override fun addStep(steps: List<Step>,id:Long){
        recipeActions.addStep(steps,id)
    }

    override fun get(id: Long): RecipeEntity =
        recipeActions.getById(id)


}