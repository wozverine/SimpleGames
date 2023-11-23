package com.glitch.simplegames.ui.tictactoe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.databinding.FragmentTictactoeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicTacToeFragment : Fragment(R.layout.fragment_tictactoe) {
    private val binding by viewBinding(FragmentTictactoeBinding::bind)
    private val viewModel by viewModels<TicTacToeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel

        with(binding){

        }
    }

}