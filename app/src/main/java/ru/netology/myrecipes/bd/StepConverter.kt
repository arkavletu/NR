package ru.netology.myrecipes.bd

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.myrecipes.Step


class StepConverter {
    @TypeConverter
    fun mapListToString(value: List<Step>): String {
    val gson = Gson()
    val type = object : TypeToken<List<Step>>() {}.type
    return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<Step> {
        val gson = Gson()
        val type = object : TypeToken<List<Step>>() {}.type
        return gson.fromJson(value, type)
    }
}
