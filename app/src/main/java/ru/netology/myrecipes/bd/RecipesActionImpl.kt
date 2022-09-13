package ru.netology.myrecipes.bd

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.myrecipes.Recipe

class RecipesActionImpl(
    private val bd: SQLiteDatabase
): RecipesActions {

    override fun getAll() = bd.query(
        RecipesTable.NAME,
        RecipesTable.ALL_COLUMNS_NAMES,
        null, null, null, null,
        "${RecipesTable.Columns.ID.columnName} DESC"
        ).use{cursor ->
          List(cursor.count){
              cursor.moveToNext()
              cursor.toRecipe()

          }
        }


    override fun save(recipe: Recipe): Recipe {
        val values = ContentValues().apply{
            put(RecipesTable.Columns.AUTHOR.columnName, recipe.author)
            put(RecipesTable.Columns.NAME.columnName, recipe.name)
            put(RecipesTable.Columns.NAME.columnName, recipe.name)
            put(RecipesTable.Columns.CATEGORY.columnName, recipe.category)
        }
        val id = if(recipe.id != 0L){
            bd.update(
                RecipesTable.NAME,
                values,
                "${RecipesTable.Columns.ID.columnName} = ?",
                arrayOf(recipe.id.toString())
            )
            recipe.id
        } else {
            bd.insert(RecipesTable.NAME,null,values)
        }
        return bd.query(
            RecipesTable.NAME,
            RecipesTable.ALL_COLUMNS_NAMES,
            "${RecipesTable.Columns.ID.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null
        ).use{cursor ->
            cursor.moveToNext()
            cursor.toRecipe()

        }
    }

    override fun addToFavorites(id: Long) {
        bd.execSQL("""
            UPDATE ${RecipesTable.NAME} SET
            ${RecipesTable.Columns.ISFAVORITE.columnName} = CASE WHEN isFavorite THEN 0 ELSE 1 END
            WHERE ${RecipesTable.Columns.ID.columnName} = ?;
        """.trimIndent(),
        arrayOf(id.toString())
        )
    }

    override fun remove(id: Long) {
        bd.delete(
            RecipesTable.NAME,
            "${RecipesTable.Columns.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }
}