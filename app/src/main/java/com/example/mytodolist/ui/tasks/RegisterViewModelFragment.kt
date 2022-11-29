package com.example.mytodolist.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Register
import com.example.mytodolist.data.models.response.Login
import com.example.mytodolist.viewModel.Repository
import kotlinx.coroutines.launch

class RegisterViewModelFragment(val repository: Repository): ViewModel() {
    private val _registerUser: MutableLiveData<NetworkResult<Login>> = MutableLiveData()
    val registerUser: LiveData<NetworkResult<Login>> = _registerUser

    fun registerUser(user: Register) = viewModelScope.launch {
        repository.registerUser(user).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerUser.value = NetworkResult.Success(it)
                    }
                }else {
                    _registerUser.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _registerUser.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}