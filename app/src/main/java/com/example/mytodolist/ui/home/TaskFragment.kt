package com.example.mytodolist.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.presenter.home.TaskFragmentViewModel
import com.example.mytodolist.presenter.home.impl.TaskFragmentViewModelImpl
import com.example.mytodolist.utils.visibilityProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TaskFragment : Fragment(R.layout.fragment_task) {

    private var _adapter: TaskAdapter? = null
    private val adapter: TaskAdapter get() = _adapter!!
    private val binding: FragmentTaskBinding by viewBinding(FragmentTaskBinding::bind)
    private val viewModel: TaskFragmentViewModel by viewModels<TaskFragmentViewModelImpl>()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifecycleScope.launchWhenResumed {
            viewModel.getAllTasks()
        }
        initAdapters()
        initObservers()
        binding.addTask.setOnClickListener {
            navController.navigate(TaskFragmentDirections.actionFragmentTaskToAddTaskFragment())
        }
    }

    private fun initAdapters() {
        _adapter = TaskAdapter()
        binding.recyclerview.adapter = adapter
    }

    private fun initObservers() {
        viewModel.getAllTasksSuccessFlow.onEach {
            visibilityProgressBar.emit(false)
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}