package com.example.mytodolist.presenter.auth.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.domain.repository.AuthRepository
import com.example.mytodolist.presenter.auth.LoginFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginFragmentViewModelImpl @Inject constructor(
    private val repo: AuthRepository
) : LoginFragmentViewModel, ViewModel() {

    override val getResponseLoginSuccessFlow = MutableSharedFlow<LoginAndRegisterResponseData>()
    override val messageFlow = MutableSharedFlow<String>()

    override suspend fun login(phone: String, password: String) {
        repo.login(phone, password).onEach {
            when (it) {
                is ResultData.Success -> {
                    getResponseLoginSuccessFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {}
            }
        }.launchIn(viewModelScope)
    }


}