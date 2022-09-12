package ru.netology.myrecipes

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.Delegates

class RecipeRepoImplFiles (

        private val application: Application
    ) : RecipeRepo {
        private val gson = Gson()
        private val type = TypeToken.getParameterized(List::class.java, Recipe::class.java).type

        private val prefs = application.getSharedPreferences("rRepo", Context.MODE_PRIVATE)
        private var nextId by Delegates.observable(
            prefs.getLong(NEXT_ID_KEY, 0L)
        ) { _, _, newValue ->
            prefs.edit { putLong(NEXT_ID_KEY, newValue) }
        }

        override val data: MutableLiveData<List<Recipe>> = MutableLiveData(emptyList())

        private var recipes
            get() = checkNotNull(data.value) { "no nullable" }
            set(value) {
                application.openFileOutput(FILE_NAME, Context.MODE_PRIVATE).bufferedWriter().use {
                    it.write(gson.toJson(value))
                }
                data.value = value

            }

        init {
            val file = application.filesDir.resolve(FILE_NAME)
            val recipes: List<Recipe> = if (file.exists()) {
                val input = application.openFileInput(FILE_NAME)
                val reader = input.bufferedReader()
                reader.use {
                    gson.fromJson(it, type)
                }
            } else emptyList()
            data.value = recipes


        }

        override fun like(rId: Long) {
            recipes = recipes.map {
                if (it.id == rId) it.copy(
                    isFavorite = !it.isFavorite,
                ) else it
            }
        }
//
//        override fun share(postId: Long) {
//            posts =
//                posts.map { if (it.id == postId) it.copy(countReposts = it.countReposts + 1) else it }
//
//        }

        override fun delete(rId: Long) {
            recipes = recipes.filter { it.id != rId }
        }

        override fun save(recipe: Recipe) {
            if (recipe.id == RecipeRepo.NEWID) insert(recipe) else update(recipe)
        }


        private fun update(recipe: Recipe) {
            recipes = recipes.map {
                if (it.id == recipe.id) recipe else it
            }
        }

        private fun insert(recipe: Recipe) {
            recipes = listOf(recipe.copy(id = ++nextId)) + recipes

        }



        private companion object {
            const val NEXT_ID_KEY = "nextId"
            const val FILE_NAME = "recipe.json"
        }

    }
