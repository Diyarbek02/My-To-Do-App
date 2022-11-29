package com.example.mytodolist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.*
import com.example.mytodolist.data.models.request.*
import com.example.mytodolist.data.retrofit.RetrofitService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: Repository) : ViewModel(){

    private val apiService = RetrofitService.apiService

    private val _registerUser: MutableLiveData<NetworkResult<com.example.mytodolist.data.models.response.Login>> = MutableLiveData()
    val registerUser: LiveData<NetworkResult<com.example.mytodolist.data.models.response.Login>> = _registerUser

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


    private val _login: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val login: LiveData<NetworkResult<loginResponse>> = _login

    fun login(user: loginRequest) {
        _login.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.login(user)

                if (response.isSuccessful) {
                    response.body()?.let { login1: loginResponse ->
                        _login.value = NetworkResult.Success(login1)
                    }
                } else {
                    _login.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _login.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }

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
                    Log.d("!!!!!","---> ${response.message()}")
                    _addTask.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _addTask.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }

    private val _getAllTask: MutableLiveData<NetworkResult<GetAllTask>> = MutableLiveData()
    val getAllTask: LiveData<NetworkResult<GetAllTask>> = _getAllTask

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

    private val _deleteTaskById: MutableLiveData<NetworkResult<Task>> = MutableLiveData()
    val deleteTaskById: LiveData<NetworkResult<Task>> = _deleteTaskById

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



}