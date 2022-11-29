package com.example.mytodolist.ui.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.Constants
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Description
import com.example.mytodolist.data.models.request.Task
import com.example.mytodolist.viewModel.Repository
import kotlinx.coroutines.launch

class AddTaskViewModelFragment(val repository: Repository): ViewModel() {
    private val _addTask: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val addTask: LiveData<NetworkResult<Task>> = _addTask

    fun addTask(token: String,description: Description) = viewModelScope.launch {
        Log.d("Token","----> ${Constants.TOKEN}")
        repository.addTask(token,description).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _addTask.value = NetworkResult.Success(it)
                    }
                }else {
                    Log.d("Quwirdaq","---> ${response.message()}")
                    _addTask.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _addTask.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}