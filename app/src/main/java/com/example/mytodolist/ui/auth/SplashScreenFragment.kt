package com.example.mytodolist.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.data.local.LocalStorage
import com.example.mytodolist.databinding.FragmentSplashScreenBinding
import com.example.mytodolist.utils.visibilityProgressBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenFragment : Fragment(R.layout.fragment_splash_screen) {

    @Inject
    lateinit var localStorage: LocalStorage
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = FragmentSplashScreenBinding.bind(view)
        binding.splash.setMinAndMaxFrame(0, 200)
        binding.splash.playAnimation()

        lifecycleScope.launchWhenResumed {
            delay(3000)
            visibilityProgressBar.emit(true)
            if (localStorage.isRegistered) {
               navController.navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToFragmentTask())
            } else {
                navController.navigate(SplashScreenFragmentDirections.actionSplashScreenFragmentToLoginFragment())
            }

        }
    }
}