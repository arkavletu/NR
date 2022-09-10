package ru.netology.myrecipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class RecipeViewModel(
    application: Application
): AndroidViewModel(application), RecipeActionListener {
    private val repo: RecipeRepo = RecipeRepoImplFiles(application)
    val data by repo::data
    val currentRecipe = MutableLiveData<Recipe?>(null)
//    val sharePost = SingleLiveEvent<String>()
    val navigateToEditScreenEvent = SingleLiveEvent<Array<String>?>()
//    val playVideoEvent = SingleLiveEvent<String?>()
//    val navigateToPostFragment = SingleLiveEvent<Long>()
//    val navigateToFirstFragment = SingleLiveEvent<Unit>()
var contentArray: Array<String> = emptyArray()
    fun onSaveClicked(array: Array<String>){
        if (array[0].isBlank()||array[1].isBlank()||array[2].isBlank()) return

        val recipe = currentRecipe.value?.copy(
            author = array[0],
            name = array[1],
            category = array[2]
        )?: Recipe(
            id = RecipeRepo.NEWID,
            author = array[0],
            name = array[1],
            category = array[2]

        )
        repo.save(recipe)
        currentRecipe.value = null
    }

//    override fun onLikeClicked(post: Post) =
//        repo.like(post.id)
//
//
//    override fun onShareClicked(post: Post) {
//        sharePost.value = post.content
//        repo.share(post.id)
//    }


    override fun onFabClicked() {
        navigateToEditScreenEvent.call()
    }


    override fun onDeleteClicked(recipe: Recipe) {
        repo.delete(recipe.id)
        //navigateToFirstFragment.call()
    }
    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToEditScreenEvent.value = arrayOf(recipe.author,recipe.name,recipe.category)

    }

//    override fun onPlayClicked(post: Post) {
//        playVideoEvent.value = post.video
//
//    }
//
//    override fun onPostClicked(id:Long){
//        navigateToPostFragment.value = id
//    }





}