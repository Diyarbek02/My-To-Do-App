package com.example.mytodolist.presenter.home

import com.example.mytodolist.data.models.task.getTaskInnerResponseData
import kotlinx.coroutines.flow.Flow

interface TaskFragmentViewModel {

    val getAllTasksSuccessFlow: Flow<List<getTaskInnerResponseData>>
    suspend fun getAllTasks()
}