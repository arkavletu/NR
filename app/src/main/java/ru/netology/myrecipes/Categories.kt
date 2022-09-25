package ru.netology.myrecipes

enum class Categories(val value: String) {
    EUROPEAN("EUROPEAN"),
    ASIAN("ASIAN"),
    PANASIATIC("PANASIATIC"),
    EASTERN("EASTERN"),
    AMERICAN("AMERICAN"),
    RUSSIAN("RUSSIAN"),
    MEDITERRANEAN("MEDITERRANEAN");

    companion object arrayOfValues {
        val array = arrayOf(
    EUROPEAN.value ,
    ASIAN.value ,
    PANASIATIC.value ,
    EASTERN.value ,
    AMERICAN.value ,
    RUSSIAN.value ,
    MEDITERRANEAN.value
    )
}
}


