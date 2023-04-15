package com.example.test_overcome.di

import android.content.Context
import com.example.test_overcome.data.local.database.TestTicketsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext
        context: Context
    ): TestTicketsDatabase{
        return TestTicketsDatabase.newInstance(context)
    }
}