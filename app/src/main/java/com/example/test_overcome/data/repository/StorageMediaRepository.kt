package com.example.test_overcome.data.repository

import android.graphics.Bitmap
import android.net.Uri
import kotlinx.coroutines.flow.Flow

interface StorageMediaRepository {
    fun getImages(): Flow<List<String?>>
    suspend fun uploadImage(image: Bitmap): String?
    fun getVideos(): Flow<List<String?>>
    suspend fun uploadVideo(videoUri: Uri): String
}