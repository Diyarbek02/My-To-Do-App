package com.example.mytodolist.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants
import com.example.mytodolist.core.Constants.TOKEN
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.data.models.request.Data
import com.example.mytodolist.databinding.FragmentTaskBinding
import com.example.mytodolist.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTask : Fragment(R.layout.fragment_task), TaskAdapter.onItemClickListener {
    private lateinit var binding: FragmentTaskBinding
    private lateinit var navController: NavController
    private val taskAdapter = TaskAdapter(this)
    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTaskBinding.bind(view)
        navController = findNavController()

        binding.apply {
            addTask.setOnClickListener {
                navController.navigate(R.id.action_fragmentTask_to_fragmentAddTask)
            }
        }

        viewModel.getAllTask.observe(requireActivity()) {
            when (it) {
                is NetworkResult.Loading -> {

                }

                is NetworkResult.Success -> {
                    taskAdapter.model = it.data?.data ?: mutableListOf()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }

            }
            Log.d("UUU", "---> ${Constants.TOKEN}")

            viewModel.getAllTask("${Constants.TOKEN}")

            binding.apply {
                addTask.setOnClickListener {
                    findNavController().navigate(R.id.action_fragmentTask_to_fragmentAddTask)
                }
            }
            taskAdapter.removeItemClick { data, position ->
                viewModel.deleteTaskById("${data.id}", "${Constants.TOKEN}")
                viewModel.deleteTaskById.observe(requireActivity()) {
                    when (it) {
                        is NetworkResult.Success -> {
                            Toast.makeText(requireContext(), "Task Deleted", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                        is NetworkResult.Loading -> {

                        }
                    }
                }
                taskAdapter.removeItem(position)
            }
            taskAdapter.setOnCheckboxClickListener {
                viewModel.updateTaskById(it.id, "Bearer $TOKEN", Completed(true))

                viewModel.updataTaskById.observe(requireActivity()) {
                    when (it) {
                        is NetworkResult.Success -> {
                            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                            viewModel.getAllTask("Bearer $TOKEN")
                        }
                        is NetworkResult.Error -> {
                            Toast.makeText(
                                requireContext(),
                                "Error Update Task",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is NetworkResult.Loading -> {

                        }
                    }
                }
            }
        }
    }

    private fun initRec() {
        binding.apply {
            recyclerview.adapter = taskAdapter
            recyclerview.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
            recyclerview.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onItemClick(task: Data) {
        val action = FragmentTaskDirections.actionFragmentTaskToFragmentUpdate(task)
        findNavController().navigate(action)
    }

    override fun onCheckBoxClick(task: Data, isChecked: Completed) {
        TODO("Not yet implemented")
    }
}