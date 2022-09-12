package ru.netology.myrecipes.bd

object RecipesTable {
    const val NAME = "recipes"

    val DDL = """CREATE TABLE $NAME (
            ${Columns.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${Columns.NAME.columnName} TEXT NOT NULL,
            ${Columns.CATEGORY.columnName} TEXT NOT NULL,
            ${Columns.AUTHOR.columnName} TEXT NOT NULL,
            ${Columns.ISFAVORITE.columnName} INTEGER NOT NULL DEFAULT 0

            
        );
        """.trimIndent()

    val ALL_COLUMNS_NAMES = Columns.values().map{it.columnName}.toTypedArray()

    enum class Columns(val columnName: String){
        ID("id"),
        NAME("name"),
        CATEGORY("category"),
        AUTHOR("author"),
        ISFAVORITE("isFavorite")

    }
}