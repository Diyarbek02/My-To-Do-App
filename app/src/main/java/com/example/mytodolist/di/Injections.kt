package com.example.mytodolist.di

import com.example.mytodolist.core.Constants
import com.example.mytodolist.data.retrofit.ApiService
import com.example.mytodolist.ui.login.LoginViewModelFragment
import com.example.mytodolist.ui.tasks.*
import com.example.mytodolist.viewModel.Repository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        OkHttpClient().newBuilder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<Repository> {
        Repository(get())
    }
}
val viewModelModule = module {
    viewModel { AddTaskViewModelFragment(get()) }
    viewModel { TaskViewModelFragment(get()) }
    viewModel { UpdateViewModelFragment(get()) }
    viewModel {RegisterViewModelFragment(get())}
    viewModel {LoginViewModelFragment(get())}
}
val adapterModule = module {
    single { TaskAdapter() }
}