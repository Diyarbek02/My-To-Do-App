package com.example.mytodolist.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants.TOKEN
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.core.loginRequest
import com.example.mytodolist.databinding.FragmentLoginBinding
import com.example.mytodolist.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        navController = findNavController()

        binding.apply {
            btnRegister.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                val loginUser = loginRequest(email, password)

                viewModel.login(loginUser)

                viewModel.login.observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }

                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Login Successful!",
                                Toast.LENGTH_SHORT
                            ).show()
                            TOKEN = it.data?.token ?: ""
                            navController.navigate(R.id.action_loginFragment_to_meFragment)
                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Snackbar.make(btnLogin, it.message.toString(), Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }
}