package ru.netology.myrecipes.bd

import android.content.Context
import androidx.room.*

@Database(
    entities = [RecipeEntity::class],

    version = 1
)
@TypeConverters(StepConverter::class)
abstract class AppBd: RoomDatabase() {
    abstract val recipesActions: RecipesActions

    companion object {
        @Volatile
        private var instance: AppBd? = null

        fun getInstance(context: Context): AppBd {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,AppBd::class.java,"AppBd"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}