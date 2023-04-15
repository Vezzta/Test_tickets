package com.example.test_overcome.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.test_overcome.utils.constants.Status

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = 1,
    val title: String? = null,
    val date: String? = null,
    val responsible: String? = null,
    val team: String? = null,
    val type: String? = null,
    val severity: String? = null,
    val version: String? = null,
    val description: String? = null,
    val files: Files? = null,
    val status: String = Status.NEW,
)
