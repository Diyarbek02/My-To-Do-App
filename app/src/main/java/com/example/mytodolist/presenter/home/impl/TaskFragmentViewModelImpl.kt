package com.example.mytodolist.presenter.home.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.data.models.task.getTaskInnerResponseData
import com.example.mytodolist.domain.repository.MainRepository
import com.example.mytodolist.presenter.home.TaskFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskFragmentViewModelImpl @Inject constructor(
    private val repository: MainRepository,
) : TaskFragmentViewModel, ViewModel() {

    override val getAllTasksSuccessFlow = MutableSharedFlow<List<getTaskInnerResponseData>>()

    override suspend fun getAllTasks() {
        repository.getAllTasks().onEach {
            when (it) {
                is ResultData.Success -> {
                    getAllTasksSuccessFlow.emit(it.data.payload)
                }
                is ResultData.Message -> {

                }
                is ResultData.Error -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        viewModelScope.launch {
            getAllTasks()
        }
    }

}