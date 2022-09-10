package ru.netology.myrecipes

    data class Recipe(
        val author: String,
        val name: String,
        val category: String,//enum
        //val steps: MutableLiveData<List<Step>> = MutableLiveData(emptyList()),
        var id: Long = 0L
    )
