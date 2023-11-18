package com.glitch.simplegames.ui.selectgame

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.databinding.FragmentSelectGameBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectGameFragment : Fragment(R.layout.fragment_select_game) {

    private val binding by viewBinding(FragmentSelectGameBinding::bind)
    private val viewModel: SelectGameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        goToGame()
    }

    private fun goToGame() {
        viewModel.selectGameState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SelectGameViewModel.SelectGameState.GoToGame -> {
                    findNavController()
                }
            }
        }
    }
}