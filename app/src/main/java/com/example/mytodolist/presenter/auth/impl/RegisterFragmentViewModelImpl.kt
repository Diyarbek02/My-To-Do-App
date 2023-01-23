package com.example.mytodolist.presenter.auth.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.data.models.LoginAndRegisterResponseData
import com.example.mytodolist.data.models.ResultData
import com.example.mytodolist.domain.repository.AuthRepository
import com.example.mytodolist.presenter.auth.RegisterFragmentViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterFragmentViewModelImpl @Inject constructor(
    private val repo: AuthRepository
) : RegisterFragmentViewModel, ViewModel() {

    override val getResponseRegisterSuccessFlow = MutableSharedFlow<LoginAndRegisterResponseData>()
    override val messageFlow = MutableSharedFlow<String>()

    override suspend fun register(name: String, phone: String, password: String) {
        repo.register(name, phone, password).onEach {
            when(it) {
                is ResultData.Success -> {
                    getResponseRegisterSuccessFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {}
            }
        }.launchIn(viewModelScope)
    }

}