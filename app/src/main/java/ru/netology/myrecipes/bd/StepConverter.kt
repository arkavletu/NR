package ru.netology.myrecipes.bd

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.myrecipes.Step


class StepConverter {
    @TypeConverter
    fun mapListToString(value: MutableList<Step>): String {
    val gson = Gson()
    val type = object : TypeToken<MutableList<Step>>() {}.type
    return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): MutableList<Step> {
        val gson = Gson()
        val type = object : TypeToken<MutableList<Step>>() {}.type
        return gson.fromJson(value, type)
    }
}
