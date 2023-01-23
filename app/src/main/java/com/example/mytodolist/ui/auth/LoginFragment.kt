package com.example.mytodolist.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.data.local.LocalStorage
import com.example.mytodolist.databinding.FragmentLoginBinding
import com.example.mytodolist.presenter.auth.LoginFragmentViewModel
import com.example.mytodolist.presenter.auth.impl.LoginFragmentViewModelImpl
import com.example.mytodolist.utils.showSnackbar
import com.example.mytodolist.utils.visibilityProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.ldralighieri.corbind.view.clicks
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    @Inject
    lateinit var localStorage: LocalStorage
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModel: LoginFragmentViewModel by viewModels<LoginFragmentViewModelImpl>()
    private lateinit var binding: FragmentLoginBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentLoginBinding.bind(view)
        initListeners()
        initObservers()

        binding.btnRegister.setOnClickListener {
            navController.navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }
    }

    private fun initListeners() {
        binding.apply {
            btnLogin.clicks().debounce(200).onEach {
                visibilityProgressBar.emit(true)
                    viewModel.login(
                        etPhone.text.toString(),
                        etPassword.text.toString()
                    )
            }.launchIn(lifecycleScope)
        }
    }

    private fun initObservers() {
        viewModel.getResponseLoginSuccessFlow.onEach {
            visibilityProgressBar.emit(false)

            localStorage.name = it.payload.name
            localStorage.token = it.payload.token
            localStorage.isRegistered = true

            navController.navigate(LoginFragmentDirections.actionLoginFragmentToFragmentTask())

        }.launchIn(lifecycleScope)

        viewModel.messageFlow.onEach {
            showSnackbar(it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)
    }
}