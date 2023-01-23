package com.example.mytodolist.domain.repository.impl

import android.util.Log
import com.example.mytodolist.data.local.LocalStorage
import com.example.mytodolist.data.models.LoginData
import com.example.mytodolist.data.models.RegisterData
import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.data.remote.MyToDoListApi
import com.example.mytodolist.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val myToDoListApi: MyToDoListApi, private val localStorage: LocalStorage
) : AuthRepository {
    override fun login(phone: String, password: String) = flow {
        Log.d("TTTT", "IT IS PHONE: +998$phone and password $password")
        val request = myToDoListApi.login(LoginData(phone, password))
        if (request.isSuccessful) {
            emit(ResultData.Success(request.body()!!))
        } else {
            emit(ResultData.Message("Error occurred while signing up"))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)

    override fun register(name: String, phone: String, password: String) = flow {
        val request = myToDoListApi.register(RegisterData(name, phone, password))
        if (request.isSuccessful) {
            emit(ResultData.Success(request.body()!!))
        } else {
            emit(ResultData.Message(request.message()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)


}