package com.example.mytodolist.domain.repository

import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.data.models.task.getTasksResponseData
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getAllTasks(): Flow<ResultData<getTasksResponseData>>
}