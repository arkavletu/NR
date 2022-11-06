package ru.netology.myrecipes

import androidx.lifecycle.LiveData

interface RecipeActionListener {
        fun onLikeClicked(recipe: Recipe)
        //fun getStepsForRecipe(id: Long): LiveData<List<Step>>
        fun onFabClicked()
        fun onDeleteClicked(recipe: Recipe)
        fun onEditClicked(recipe: Recipe)
        fun onImageClicked()
        fun onPostClicked(id:Long)
        fun get(id: Long):Recipe

}