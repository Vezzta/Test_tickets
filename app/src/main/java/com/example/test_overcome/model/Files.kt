package com.example.test_overcome.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "files")
data class Files(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val videoUrl: List<String?> = emptyList(),
    val audioUrl: List<String?> = emptyList(),
    val imageUrl: List<String?> = emptyList(),
)
