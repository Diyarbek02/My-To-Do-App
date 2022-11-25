package com.example.mytodolist.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentAddTaskBinding
import com.example.mytodolist.ui.MainViewModel

class FragmentAddTask: Fragment(R.layout.fragment_add_task) {
    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentAddTaskBinding.inflate(layoutInflater)
        navController = findNavController()
    }
}