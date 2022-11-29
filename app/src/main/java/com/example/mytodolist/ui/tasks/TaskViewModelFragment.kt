package com.example.mytodolist.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.data.models.request.GetAllTask
import com.example.mytodolist.data.models.request.Task
import com.example.mytodolist.data.models.request.UpdateTask
import com.example.mytodolist.viewModel.Repository
import kotlinx.coroutines.launch

class TaskViewModelFragment (val repository: Repository): ViewModel() {
    private val _getAllTask: MutableLiveData<NetworkResult<GetAllTask>> = MutableLiveData()
    val getAllTask: LiveData<NetworkResult<GetAllTask>> = _getAllTask

    private val _deleteTaskById: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val deleteTaskById: LiveData<NetworkResult<Task>> = _deleteTaskById

    private val _updateTaskById: MutableLiveData<NetworkResult<UpdateTask>> = MutableLiveData()
    val updataTaskById: LiveData<NetworkResult<UpdateTask>> = _updateTaskById

    fun getAllTask(token: String) = viewModelScope.launch {
        repository.getAllTask(token).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _getAllTask.value = NetworkResult.Success(it)
                    }
                }else {
                    _getAllTask.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _getAllTask.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    fun deleteTaskById(id: String, token: String) = viewModelScope.launch {
        repository.deleteTaskById(id, token).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _deleteTaskById.value = NetworkResult.Success(it)
                    }
                }else {
                    _deleteTaskById.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _deleteTaskById.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    fun updateTaskById(id: String, token: String, completed: Completed) = viewModelScope.launch {
        repository.updateTaskById(id, token, completed).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _updateTaskById.value = NetworkResult.Success(it)
                    }
                }else {
                    _updateTaskById.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _updateTaskById.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}