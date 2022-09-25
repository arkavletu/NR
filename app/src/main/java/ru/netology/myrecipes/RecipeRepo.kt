package ru.netology.myrecipes

import androidx.lifecycle.LiveData
import ru.netology.myrecipes.bd.RecipeEntity

interface RecipeRepo {
    val data: LiveData<List<Recipe>>
    val steps: LiveData<List<Step>>
    fun getStepsForRecipe(id: Long): LiveData<List<Step>>
    fun like(rId: Long)
    fun getFavorites(): LiveData<List<Recipe>>
    fun getFiltered(category:String?): LiveData<List<Recipe?>>
    fun delete(rId: Long)
    fun save(recipe: Recipe): RecipeEntity
    fun insertStep(step:Step)
    fun updateStep(id: Long)

    companion object{
        const val NEWID = 0L
    }
}