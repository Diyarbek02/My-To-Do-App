package com.example.mytodolist.domain.repository

import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import com.example.mytodolist.data.models.ResultData
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(phone: String, password: String): Flow<ResultData<LoginAndRegisterResponseData>>

    fun register(name: String, phone: String, password: String): Flow<ResultData<LoginAndRegisterResponseData>>
}