package com.example.test_overcome.data.local.converters

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(string: String): List<String> {
        return string.split(",")
    }
}