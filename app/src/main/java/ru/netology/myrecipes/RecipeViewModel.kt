package ru.netology.myrecipes

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import ru.netology.myrecipes.bd.AppBd
import ru.netology.myrecipes.bd.toModel
import ru.netology.myrecipes.bd.toStepEntity

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeActionListener {
    private val repo: RecipeRepo = SQLiteRepo(
        recipeActions = AppBd.getInstance(
            context = application
        ).recipesActions
    )
    var currentFavorites = repo.getFavorites()
    val data by repo::data
    val steps by repo::steps
    var currentRecipe = MutableLiveData<Recipe?>(null)
    val navigateToEditScreenEvent = SingleLiveEvent<Array<String>?>()
    val navigateToNewScreenEvent = SingleLiveEvent<Unit>()
    val ImageEvent = SingleLiveEvent<Unit>()
    val navigateToPostFragment = SingleLiveEvent<Long>()
    val navigateToFavoritesFragment = SingleLiveEvent<Unit>()
    var contentArray: Array<String> = emptyArray()
    val addStepEvent = SingleLiveEvent<Unit>()
    var currentSteps = MutableLiveData<List<Step?>>(null)
    //fun getRecipeAndSteps(id: Long) = repo.getRecipeAndSteps(id)
    fun onSaveClicked(array: Array<String>) {
        if (array[0].isBlank() || array[1].isBlank() || array[2].isBlank()) return

        val recipe = currentRecipe.value?.copy(
            author = array[0],
            name = array[1],
            category = array[2],
            imageUrl = array[3]

        ) ?: Recipe(
            id = RecipeRepo.NEWID,
            author = array[0],
            name = array[1],
            category = array[2],
            imageUrl = array[3]
        )
        repo.save(recipe)
        currentRecipe.value = recipe
        repo.steps.value?.forEach {
            if (it.recipeId == 0L) repo.updateStep(currentRecipe.value?.id ?: return)
        }
//        repo.save(recipe)
//        //currentRecipe.value = recipe
//        currentSteps.value?.forEach {
//            if (it?.recipeId == 0L) {
//
//                repo.updateStep(currentRecipe.value?.id?:recipe.id)
//            }else{
//                it?.copy(recipeId = recipe.id)?.let { it1 -> repo.insertStep(it1) }
//            }
//
//        }

        currentRecipe.value = null
    }

    override fun onLikeClicked(recipe: Recipe) =
        repo.like(recipe.id)



    override fun onFabClicked() {
        navigateToNewScreenEvent.call()
    }


    override fun onDeleteClicked(recipe: Recipe) {
        repo.delete(recipe.id)
    }

    override fun onEditClicked(recipe: Recipe) {
        currentRecipe.value = recipe
        navigateToEditScreenEvent.value = arrayOf(recipe.author, recipe.name, recipe.category)

    }

    override fun onImageClicked() {
        ImageEvent.call()

    }


    override fun onPostClicked(id: Long) {
        navigateToPostFragment.value = id

    }

    fun getFiltered(category: String) = repo.getFiltered(category)
    fun getFilteredFavorites(category: String) = repo.getFilteredFavorites(category)


    fun addStep(){
        addStepEvent.call()
    }


    fun saveStep(text:String, uri:String){
       val step = Step(text = text, imageUrl = uri, recipeId = currentRecipe.value?.id?:0L,id = 0L)
       // currentSteps.value = currentSteps.value?.plus(listOf(step))
        repo.insertStep(step)
    }

    override fun getStepsForRecipe(id: Long) = repo.getStepsForRecipe(id)


}