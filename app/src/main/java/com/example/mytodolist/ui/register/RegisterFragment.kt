package com.example.mytodolist.ui.register

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants.TOKEN
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Register
import com.example.mytodolist.databinding.FragmentRegisterBinding
import com.example.mytodolist.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        navController = findNavController()
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)

        binding.apply {

            btnRegister.setOnClickListener {
                val phone = etPhone.text.toString()
                val password = etPassword.text.toString()
                val name = etName.text.toString()

                val user = Register(name, phone, password)
                viewModel.registerUser(user)

                viewModel.registerUser.observe(viewLifecycleOwner) {
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
                            TOKEN = it.data?.token ?: ""
                            navController.navigate(R.id.action_registerFragment_to_fragmentTask)
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