package com.example.mytodolist.di

import android.content.Context
import com.example.mytodolist.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModule {

    @Provides
    fun localStorageProviders(@ApplicationContext context: Context): LocalStorage =
        LocalStorage(context)
}