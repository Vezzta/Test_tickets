package com.example.test_overcome.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @Singleton
    @Provides
    fun providesFirebaseDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()
    }

    @Singleton
    @Provides
    fun providesFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun providesFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

}