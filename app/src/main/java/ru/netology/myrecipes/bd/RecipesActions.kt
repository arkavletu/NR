package ru.netology.myrecipes.bd

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipesActions {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavorites(): LiveData<List<RecipeEntity>>

    @Insert
    fun insert(recipe: RecipeEntity)

    @Query("UPDATE recipes SET author = :author WHERE id = :id")// и остальные поля
    fun updateContentById(id: Long, author: String)

//    fun save(recipe: RecipeEntity) =
//        if (recipe.id == 0L) insert(recipe) else updateContentById(recipe.id, recipe.author)

    @Query("""
        UPDATE recipes SET
        isFavorite = CASE WHEN isFavorite THEN 0 ELSE 1 END
        WHERE id = :id
        """)
    fun addToFavorites(id: Long)

    @Query("DELETE FROM recipes WHERE id = :id")
    fun removeById(id: Long)


//    fun getAll(): List<Recipe>
//    fun save(recipe: Recipe): Recipe
//    fun addToFavorites(id:Long)
//    fun remove(id:Long)
}