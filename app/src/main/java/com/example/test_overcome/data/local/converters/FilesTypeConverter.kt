package com.example.test_overcome.data.local.converters

import androidx.room.TypeConverter
import com.example.test_overcome.model.Files
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FilesTypeConverter {
    @TypeConverter
    fun fromFiles(files: Files): String {
        val json = Gson().toJson(files)
        return json
    }

    @TypeConverter
    fun toFiles(json: String): Files {
        val type = object : TypeToken<Files>() {}.type
        val files = Gson().fromJson<Files>(json, type)
        return files
    }
}