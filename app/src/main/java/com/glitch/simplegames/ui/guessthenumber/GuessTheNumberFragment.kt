package com.glitch.simplegames.ui.guessthenumber

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.databinding.FragmentGuessthenumberBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuessTheNumberFragment: Fragment(R.layout.fragment_guessthenumber) {
    private val binding by viewBinding(FragmentGuessthenumberBinding::bind)
    //private val viewModel by viewModels<GuessTheNumberViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel

        with(binding){

        }
    }
}