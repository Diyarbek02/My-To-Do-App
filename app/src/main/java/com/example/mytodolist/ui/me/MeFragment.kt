package com.example.mytodolist.ui.me

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.core.NetworkResult
import com.example.mytodolist.databinding.FragmentMeBinding
import com.example.mytodolist.ui.MainViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MeFragment : Fragment(R.layout.fragment_me) {
    private lateinit var binding: FragmentMeBinding
    private lateinit var navController: NavController
    private val viewModel by lazy { MainViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeBinding.bind(view)
    }
}