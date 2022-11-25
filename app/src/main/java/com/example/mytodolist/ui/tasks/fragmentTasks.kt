package com.example.mytodolist.ui.tasks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.ui.MainViewModel

class fragmentTasks: Fragment(R.layout.fragment_task) {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTaskBinding.inflate(layoutInflater)
        navController = findNavController()
    }
}