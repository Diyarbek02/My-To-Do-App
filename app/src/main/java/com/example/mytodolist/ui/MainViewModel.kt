package com.example.mytodolist.ui

import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.*
import com.example.mytodolist.core.Constants.TOKEN
import com.example.mytodolist.data.models.request.Register
import com.example.mytodolist.data.models.response.User
import com.example.mytodolist.data.retrofit.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainViewModel : ViewModel(){
    private val apiService = RetrofitService.apiService

    private val _register: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val register: LiveData<NetworkResult<loginResponse>> = _register

    fun register(user: Register) {
        _register.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.registerUser(user)

                if ( response.isSuccessful) {
                    response.body()?.let {
                        _register.value = NetworkResult.Success(it)
                    }
                }else {
                    _register.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _register.value = NetworkResult.Error(e.localizedMessage)
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

    private val _me: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val me: LiveData<NetworkResult<User>> = _me

    fun me() {
        _me.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.me("Bearer $TOKEN")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _me.value = NetworkResult.Success(it)
                    }
                } else {
                    _me.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _me.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }

    private val _update: MutableLiveData<NetworkResult<updateResponse>> = MutableLiveData()
    val update: LiveData<NetworkResult<updateResponse>> = _update

    fun update(user: updateRequest) {
        _update.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.updateMe("Bearer $TOKEN", user)

                if (response.isSuccessful) {
                    response.body()?.let {
                        _update.value = NetworkResult.Success(it)
                    }
                } else {
                    _update.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _update.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }

    private val _delete: MutableLiveData<NetworkResult<User>> = MutableLiveData()
    val delete: LiveData<NetworkResult<User>> = _delete

    fun deleteMe() {
        _delete.value = NetworkResult.Loading()
        viewModelScope.launch {
            try {
                val response = apiService.deleteMe("Bearer $TOKEN")

                if (response.isSuccessful) {
                    response.body()?.let {
                        _delete.value = NetworkResult.Success(it)
                    }
                } else {
                    _delete.value = NetworkResult.Error(response.message())
                }
            } catch (e: Exception) {
                _delete.value = NetworkResult.Error(e.localizedMessage)
            }
        }
    }
}