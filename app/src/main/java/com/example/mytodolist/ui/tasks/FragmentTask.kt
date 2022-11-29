package com.example.mytodolist.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants.TOKEN
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.data.models.request.Data
import com.example.mytodolist.databinding.FragmentTaskBinding
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentTask : Fragment(R.layout.fragment_task){
    private lateinit var binding: FragmentTaskBinding
    private lateinit var navController: NavController
    private val taskAdapter = TaskAdapter by inject()
    private val taskViewModelFragment: TaskViewModelFragment by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTaskBinding.bind(view)
        navController = findNavController()

        initRec()

        binding.apply {
            addTask.setOnClickListener {
                findNavController().navigate(R.id.action_fragmentTask_to_fragmentAddTask)
            }
        }
        taskViewModelFragment.getAllTask.observe(requireActivity()) { result ->
            when (result) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    taskAdapter.model = result.data?.data ?: mutableListOf()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }

            Log.d("UUU", "---> $TOKEN")

            taskViewModelFragment.getAllTask(TOKEN)

            taskAdapter.removeItemClick { data, position ->
                taskViewModelFragment.deleteTaskById(data.id, TOKEN)
                taskViewModelFragment.deleteTaskById.observe(requireActivity()) { result ->
                    when (result) {
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
            taskAdapter.setOnCheckboxClickListener { data ->
                taskViewModelFragment.updateTaskById(data.id, "Bearer $TOKEN", Completed(true))

                taskViewModelFragment.updataTaskById.observe(requireActivity()) {
                    when (it) {
                        is NetworkResult.Success -> {
                            Toast.makeText(requireContext(), "Updated", Toast.LENGTH_SHORT).show()
                            taskViewModelFragment.getAllTask("Bearer $TOKEN")
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
        taskAdapter.onItemClick {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(it)
            findNavController().navigate(action)
        }z
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
}
