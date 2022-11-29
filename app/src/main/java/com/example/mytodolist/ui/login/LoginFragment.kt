package com.example.mytodolist.ui.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.core.loginRequest
import com.example.mytodolist.databinding.FragmentLoginBinding
import com.example.mytodolist.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        navController = findNavController()

        binding.apply {
            btnRegister.setOnClickListener {
                navController.navigate(R.id.action_loginFragment_to_registerFragment)
            }

            btnLogin.setOnClickListener {
                val email = etPhone.text.toString()
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
                            navController.navigate(R.id.action_loginFragment_to_fragmentTask)
                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Failed authorization!",
                                Toast.LENGTH_SHORT
                            ).show()
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