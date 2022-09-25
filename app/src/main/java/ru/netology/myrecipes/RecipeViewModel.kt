package ru.netology.myrecipes

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import ru.netology.myrecipes.bd.AppBd

class RecipeViewModel(
    application: Application
) : AndroidViewModel(application), RecipeActionListener {
    private val repo: RecipeRepo = SQLiteRepo(
        recipeActions = AppBd.getInstance(
            context = application
        ).recipesActions
    )
    var currentFavorites = repo.getFavorites()
    var newUri: Uri? = null
    val data by repo::data
    val currentRecipe = MutableLiveData<Recipe?>(null)
    val navigateToEditScreenEvent = SingleLiveEvent<Array<String>?>()
    val ImageEvent = SingleLiveEvent<Unit>()
    val navigateToPostFragment = SingleLiveEvent<Long>()
    val navigateToFavoritesFragment = SingleLiveEvent<Unit>()
    var contentArray: Array<String> = emptyArray()
    val addStepEvent = SingleLiveEvent<Unit>()
    val stepsMap = MutableLiveData<Map<Long,List<Step>>>()
    var currentSteps = MutableLiveData<MutableList<Step?>>(null)

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
        currentSteps.value?.apply {
            this.forEach {
            it?.recipeId = repo.save(recipe).id//or update
                if (it != null) {
                    repo.insertStep(it)
                }
        }
        }

        currentRecipe.value = null
    }

    override fun onLikeClicked(recipe: Recipe) =
        repo.like(recipe.id)



    override fun onFabClicked() {
        navigateToEditScreenEvent.call()
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

    fun addStep(){
        addStepEvent.call()
    }

    fun saveStep(text:String, uri:String?){
       val step = Step(text = text, imageUrl = "uri")
        repo.insertStep(step)
        currentSteps.value?.plusAssign(step)
    }


}