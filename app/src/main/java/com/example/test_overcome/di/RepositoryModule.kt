package com.example.test_overcome.di

import com.example.test_overcome.data.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLocalTicketRepository(ticketRepositoryImp: LocalTicketRepositoryImp): LocalTicketRepository

    @Binds
    abstract fun bindAuthRepository(authRepositoryImp: AuthRepositoryImp): AuthRepository

    @Binds
    abstract fun bindStorageMediaRepository(firebaseTicketRepositoryImp: StorageMediaRepositoryImp): StorageMediaRepository

    @Binds
    abstract fun bindRemoteTicketRepository(remoteTicketRepositoryImp: RemoteTicketRepositoryImp): RemoteTicketRepository

}