package com.example.mytodolist.presenter.auth

import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import kotlinx.coroutines.flow.Flow


interface RegisterFragmentViewModel {

    val getResponseRegisterSuccessFlow: kotlinx.coroutines.flow.Flow<LoginAndRegisterResponseData>
    val messageFlow: Flow<String>

    suspend fun register(name: String, phone: String, password: String)
}