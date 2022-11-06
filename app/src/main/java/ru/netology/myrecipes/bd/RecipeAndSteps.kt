package ru.netology.myrecipes.bd
//
//import androidx.room.Embedded
//import androidx.room.Relation
//import ru.netology.myrecipes.Recipe
//import ru.netology.myrecipes.Step
//
//data class RecipeAndSteps(
//    @Embedded val recipe: Recipe,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "recipeId"
//    )
//    val steps: List<Step>
//)