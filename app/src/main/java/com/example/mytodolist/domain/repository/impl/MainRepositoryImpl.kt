package com.example.mytodolist.domain.repository.impl

import com.example.mytodolist.data.local.LocalStorage
import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.data.models.task.getTasksResponseData
import com.example.mytodolist.data.remote.MyToDoListApi
import com.example.mytodolist.domain.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepositoryImpl @Inject constructor(private val api: MyToDoListApi,
private val localStorage: LocalStorage): MainRepository {


    override fun getAllTasks() = flow {
        val request = api.getTasks("Bearer ${localStorage.token}")
        if (request.isSuccessful) {
            emit(ResultData.Success(request.body()!!))
        }
        else {
            emit(ResultData.Message(request.message()))
        }

    }.catch{
        emit(ResultData.Error(it))
    }.flowOn(Dispatchers.IO)


}