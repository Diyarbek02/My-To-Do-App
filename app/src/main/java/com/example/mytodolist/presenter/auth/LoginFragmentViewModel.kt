package com.example.mytodolist.presenter.auth

import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import kotlinx.coroutines.flow.Flow

interface LoginFragmentViewModel {

    val getResponseLoginSuccessFlow: Flow<LoginAndRegisterResponseData>
    val messageFlow: Flow<String>

    suspend fun login(phone: String, password: String)

}