package com.example.mytodolist.ui.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Register
import com.example.mytodolist.databinding.FragmentRegisterBinding
import com.example.mytodolist.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        navController = findNavController()

        binding.apply {

            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val name = etName.text.toString()

                val user = Register(name, email, password)
                viewModel.register(user)

                viewModel.register.observe(viewLifecycleOwner) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }

                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(
                                requireContext(),
                                "Register Successful!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            Constants.TOKEN = it.data?.token ?: ""
                            navController.navigate(R.id.action_registerFragment_to_meFragment)
                        }

                        is NetworkResult.Error -> {
                            setLoading(false)
                            Snackbar.make(btnRegister, it.message.toString(), Snackbar.LENGTH_SHORT)
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