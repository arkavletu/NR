package ru.netology.myrecipes.bd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.netology.myrecipes.Recipe
import ru.netology.myrecipes.Step

@Entity(tableName = "recipes")
class RecipeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    val author: String,
    val name: String,
    val category: String,
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @TypeConverters(StepConverter::class)
    @ColumnInfo(defaultValue = "No value")
    val steps: List<Step>
){}
fun RecipeEntity.toModel() = Recipe(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite,
    imageUrl = imageUrl,
    steps = steps
)

fun Recipe.toEntity() = RecipeEntity(
    id = id,
    name = name,
    author = author,
    category = category,
    isFavorite = isFavorite,
    imageUrl = imageUrl,
    steps = steps

)