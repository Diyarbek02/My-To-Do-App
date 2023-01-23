package com.example.mytodolist.di

import com.example.mytodolist.domain.repository.AuthRepository
import com.example.mytodolist.domain.repository.MainRepository
import com.example.mytodolist.domain.repository.impl.AuthRepositoryImpl
import com.example.mytodolist.domain.repository.impl.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindMainRepository(impl: MainRepositoryImpl): MainRepository
}