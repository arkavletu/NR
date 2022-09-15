package ru.netology.myrecipes.bd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
class RecipeEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    val author: String,
    val name: String,
    val category: String,//enum
    //val steps: MutableLiveData<List<Step>> = MutableLiveData(emptyList()),
    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
)