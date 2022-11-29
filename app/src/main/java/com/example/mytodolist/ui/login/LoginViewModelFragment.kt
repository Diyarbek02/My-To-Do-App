package com.example.mytodolist.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.core.loginRequest
import com.example.mytodolist.core.loginResponse
import com.example.mytodolist.data.retrofit.RetrofitService.apiService
import com.example.mytodolist.viewModel.Repository
import kotlinx.coroutines.launch

class LoginViewModelFragment(val repository: Repository) : ViewModel() {

    private val _login: MutableLiveData<NetworkResult<loginResponse>> = MutableLiveData()
    val login: LiveData<NetworkResult<loginResponse>> = _login

    fun login(user: loginRequest) = viewModelScope.launch {
        repository.login(user).let { response ->
            try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        _login.value = NetworkResult.Success(it)
                    }
                }else {
                    _login.value = NetworkResult.Error(response.message())
                }
            }catch (e: Exception) {
                _login.value = e.localizedMessage?.let { NetworkResult.Error(it) }
            }
        }
    }
}
