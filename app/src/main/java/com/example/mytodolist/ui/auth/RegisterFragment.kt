package com.example.mytodolist.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytodolist.MainActivity
import com.example.mytodolist.R
import com.example.mytodolist.data.local.LocalStorage
import com.example.mytodolist.databinding.FragmentRegisterBinding
import com.example.mytodolist.presenter.auth.RegisterFragmentViewModel
import com.example.mytodolist.presenter.auth.impl.RegisterFragmentViewModelImpl
import com.example.mytodolist.utils.visibilityProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    @Inject
    lateinit var localStorage: LocalStorage
    private val binding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: RegisterFragmentViewModel by viewModels<RegisterFragmentViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.getResponseRegisterSuccessFlow.onEach {
            visibilityProgressBar.emit(false)
            localStorage.name = it.payload.name
            localStorage.token = it.payload.token
            localStorage.phone = it.payload.phone
            localStorage.isRegistered = true
            //open next screen
            navController.navigate(RegisterFragmentDirections.actionRegisterFragmentToFragmentTask())
        }.launchIn(lifecycleScope)
    }

    private fun initListeners() {
        binding.btnRegister.clicks().debounce(200).onEach {
            visibilityProgressBar.emit(true)
            val name = binding.etName.text.toString()
            val phone = binding.etPhone.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.register(name, phone, password)

        }.launchIn(lifecycleScope)
    }

}