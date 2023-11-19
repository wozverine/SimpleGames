package com.glitch.simplegames.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.selectGame()
        goToGame()
    }

    private fun goToGame() = with(binding) {
        viewModel.selectGameState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeViewModel.SelectGameState.GoToGame -> {
                    findNavController()
                }

                else -> {}
            }
        }
    }
}