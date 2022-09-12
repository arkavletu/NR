package ru.netology.myrecipes.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.netology.myrecipes.bd.RecipesTable.DDL

class AppBd private constructor(bd: SQLiteDatabase){
    val recipesActions: RecipesActions = RecipesActionImpl(bd)

    companion object {
        @Volatile
        private var instance: AppBd? = null

        fun getInstance(context: Context): AppBd {
            return instance ?: synchronized(this) {
                instance ?: AppBd(
                    buildDatabase(context, arrayOf(RecipesTable.DDL))
                ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context, DDLs: Array<String>) = BdHelper(
            context, 1, "app.bd", DDLs,
        ).writableDatabase
    }
}