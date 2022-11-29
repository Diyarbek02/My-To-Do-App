package com.example.mytodolist.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.data.models.request.UpdateTask
import com.example.mytodolist.viewModel.Repository
import kotlinx.coroutines.launch

class UpdateViewModelFragment(val repository: Repository): ViewModel() {

    private val _updateTaskById: MutableLiveData<NetworkResult<UpdateTask>> = MutableLiveData()
    val updataTaskById: LiveData<NetworkResult<UpdateTask>> = _updateTaskById

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