package ru.netology.myrecipes.bd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
class StepEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name="text")
    val text: String,
    @ColumnInfo(name="imageUrl")
    val imageUrl: String? = "null",
    @ColumnInfo(name="recipeId")
    val recipeId: Long
)