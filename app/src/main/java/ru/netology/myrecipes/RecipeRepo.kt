package ru.netology.myrecipes

import androidx.lifecycle.LiveData
import ru.netology.myrecipes.bd.RecipeEntity

interface RecipeRepo {
    val data: LiveData<List<Recipe>>
    fun like(rId: Long)
    fun getFavorites(): LiveData<List<Recipe>>
    fun getFiltered(category:String?): LiveData<List<Recipe?>>
    //    fun share(rId: Long)
    fun delete(rId: Long)
    fun save(recipe: Recipe): RecipeEntity
    fun insertStep(step:Step)
    // fun cansel(post: Post)

    companion object{
        const val NEWID = 0L
    }
}