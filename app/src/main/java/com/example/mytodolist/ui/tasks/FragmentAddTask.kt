package com.example.mytodolist.ui.tasks

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Description
import com.example.mytodolist.databinding.FragmentAddTaskBinding
import com.example.mytodolist.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddTask: Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddTaskBinding.bind(view)
        sharedPreferences = requireActivity().getSharedPreferences("shared", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        binding.apply {
            btnSave.setOnClickListener {
                viewModel.addTask.observe(requireActivity()) {
                    when (it) {
                        is NetworkResult.Loading -> {
                            setLoading(true)
                        }
                        is NetworkResult.Success -> {
                            setLoading(false)
                            Toast.makeText(requireContext(), "Task Saved", Toast.LENGTH_SHORT)
                                .show()
                            findNavController().navigate(R.id.action_fragmentAddTask_to_fragmentTask)
                        }
                        is NetworkResult.Error -> {
                            setLoading(false)
                            Toast.makeText(requireContext(), "No saved", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
                Log.d("SSS","-----> ${token.toString()}")
                viewModel.addTask("Bearer $token", Description(etTask.text.toString()))
            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.progressBar.isVisible = loading
    }
}