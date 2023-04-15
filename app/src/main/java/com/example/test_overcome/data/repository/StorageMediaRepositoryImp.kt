package com.example.test_overcome.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*
import javax.inject.Inject

class StorageMediaRepositoryImp @Inject constructor(
    private val firebaseStorage: FirebaseStorage
): StorageMediaRepository {
    override fun getImages(): Flow<List<String>> = flow {
        val storageRef = firebaseStorage.reference.child("images")
        val images = mutableListOf<String>()
        storageRef.listAll().await().items.forEach { ref ->
            val url = ref.downloadUrl.await().toString()
            images.add(url)
        }
        emit(images)
    }

    override suspend fun uploadImage(image: Bitmap): String {
        val storageRef = firebaseStorage.reference.child("images/${UUID.randomUUID()}.png")
        val stream = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val data = stream.toByteArray()
        val uploadTask = storageRef.putBytes(data)
        val taskSnapshot = uploadTask.await()
        return taskSnapshot.storage.downloadUrl.await().toString()
    }

    override fun getVideos(): Flow<List<String>> = flow {
        val storageRef = firebaseStorage.reference.child("videos")
        val videos = mutableListOf<String>()
        storageRef.listAll().await().items.forEach { ref ->
            val url = ref.downloadUrl.await().toString()
            videos.add(url)
        }
        emit(videos)
    }

    override suspend fun uploadVideo(videoUri: Uri): String {
        val storageRef = firebaseStorage.reference.child("videos/${UUID.randomUUID()}.mp4")
        val uploadTask = storageRef.putFile(videoUri)
        val taskSnapshot = uploadTask.await()
        return taskSnapshot.storage.downloadUrl.await().toString()
    }

}