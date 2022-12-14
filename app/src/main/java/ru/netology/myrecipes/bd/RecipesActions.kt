package ru.netology.myrecipes.bd

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.myrecipes.Step

@Dao
@TypeConverters(StepConverter::class)
interface RecipesActions {

//    @Transaction
//    @Query("SELECT * FROM recipes WHERE id = :id")
//    fun getRecipesAndSteps(id: Long): LiveData<List<RecipeAndSteps>>

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

//    @Query("SELECT * FROM steps ORDER BY id DESC")
//    fun getAllSteps(): LiveData<List<StepEntity>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavorites(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

//    @Insert
//    fun insertStep(step: StepEntity)
//
//    @Query("SELECT * FROM steps WHERE recipeId = :id")//order by?
//    fun getStepsForRecipe(id: Long): LiveData<List<StepEntity>>

    @Query("UPDATE recipes SET author = :author, name = :name, category = :category, imageUrl = :imageUrl, steps = :steps WHERE id = :id")
    fun updateContentById(id: Long, author: String, name: String, category: String, imageUrl: String?, steps: List<Step>?)



//    @Query("UPDATE steps SET recipeId = :id WHERE id = 0")
//    fun updateStepRecipeId(id: Long)

//    fun save(recipe: RecipeEntity): RecipeEntity =
//        if (recipe.id == 0L) insert(recipe) else updateContentById(recipe.id, recipe.author, recipe.name: String, recipe.category: String, recipe.imageUrl: String?)

    @Query("""
        UPDATE recipes SET
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun addToFavorites(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)

    @Query("SELECT*FROM recipes WHERE id = :id")
    fun getById(id:Long): RecipeEntity

    @Query("UPDATE recipes SET steps = :steps WHERE id = :id")
    fun addStep(steps:List<Step>,id:Long)

    @Query("SELECT*FROM recipes WHERE category = :category")
    fun selectOneCategory(category: String):LiveData<List<RecipeEntity>>

    @Query("SELECT*FROM recipes WHERE category = :category AND isFavorite = 1")
    fun selectOneCategoryFromFavorites(category: String):LiveData<List<RecipeEntity>>


}