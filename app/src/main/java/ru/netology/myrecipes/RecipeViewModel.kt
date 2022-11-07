package ru.netology.myrecipes

import android.app.Application
import androidx.lifecycle.*
import ru.netology.myrecipes.bd.AppBd
import ru.netology.myrecipes.bd.toModel

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
    //val steps by repo::steps
    var currentRecipe = MutableLiveData<Recipe?>()
    val navigateToEditScreenEvent = SingleLiveEvent<Array<String?>?>()
    val navigateToNewScreenEvent = SingleLiveEvent<Unit>()
    val ImageEvent = SingleLiveEvent<Unit>()
    val navigateToPostFragment = SingleLiveEvent<Long>()
    val navigateToFavoritesFragment = SingleLiveEvent<Unit>()
    var contentArray: Array<String> = emptyArray()
    val addStepEvent = SingleLiveEvent<Unit>()
    var currentSteps = MutableLiveData<MutableList<Step>>(null)
    //fun getRecipeAndSteps(id: Long) = repo.getRecipeAndSteps(id)
    fun onSaveClicked(array: Array<String>) {
        if (array[0].isBlank() || array[1].isBlank() || array[2].isBlank()) return

        val recipe = currentRecipe.value?.copy(
            author = array[0],
            name = array[1],
            category = array[2],
            imageUrl = array[3],
        ) ?: currentSteps.value?.toList()?.let {
            Recipe(
                id = RecipeRepo.NEWID,
                author = array[0],
                name = array[1],
                category = array[2],
                imageUrl = array[3],
                steps = it
            )
        }
        if (recipe != null) {
            repo.save(recipe)
        }
        currentRecipe.value = recipe
//        repo.steps.value?.forEach {
//            if (it.recipeId == 0L) repo.updateStep(currentRecipe.value?.id ?: return)
//        }
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

        //currentRecipe.value = null
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

    override fun get(id: Long) =
        repo.get(id).toModel()


    fun getFiltered(category: String) = repo.getFiltered(category)
    fun getFilteredFavorites(category: String) = repo.getFilteredFavorites(category)


    fun addStep(){
        addStepEvent.call()
    }


    fun saveStep(text:String, uri:String){
        if (currentRecipe.value!=null) {
            val step = Step(text, uri, recipeId = currentRecipe.value!!.id)
            val steps = currentSteps.value
            steps?.plus(step)
            currentSteps.value = steps!!
            //currentRecipe.value!!.steps = currentSteps.value.toList()
        }
//        val newRecipe = currentRecipe.value?.id?.let { repo.get(it) }?.toModel()
//        if (step != null) {
//            newRecipe?.steps = newRecipe?.steps?.plus(step)!!
//            currentRecipe.value = newRecipe
//        }
//        if (newRecipe != null) {
//            repo.save(newRecipe)
//        }

    }

    //override fun getStepsForRecipe(id: Long) = repo.getStepsForRecipe(id)


}