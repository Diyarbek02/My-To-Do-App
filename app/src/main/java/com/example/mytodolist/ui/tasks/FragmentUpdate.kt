package com.example.mytodolist.ui.tasks

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mytodolist.R
import com.example.mytodolist.core.Constants
import com.example.mytodolist.data.models.request.Completed
import com.example.mytodolist.databinding.FragmentUpdateBinding
import com.example.mytodolist.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class FragmentUpdate: Fragment(R.layout.fragment_update) {
    private lateinit var binding: FragmentUpdateBinding
    private val updateViewModelFragment: UpdateViewModelFragment by viewModel()
    private val args: FragmentUpdateArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdateBinding.bind(view)

        binding.apply {
            val update = args.task.description
            Log.d("TTT","----> $update")
            btnUpdate.text = update

            btnUpdate.setOnClickListener {
                updateViewModelFragment.updateTaskById(args.task.id, Constants.TOKEN, Completed(true))
                findNavController().navigate(R.id.action_fragmentUpdate_to_fragmentTask)
            }
        }
    }

}