package com.glitch.simplegames.ui.rockpaper

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.glitch.simplegames.R
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.databinding.FragmentRockpaperBinding

class RockPaperFragment : Fragment(R.layout.fragment_rockpaper) {
	private val binding by viewBinding(FragmentRockpaperBinding::bind)
	private val viewModel by viewModels<RockPaperViewModel>()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

	}

	private fun playGame() = with(binding) {
		viewModel.rockPaperState.observe(viewLifecycleOwner) { state ->
			when (state) {
				RockPaperViewModel.RockPaperState.Loading -> {

				}

				RockPaperViewModel.RockPaperState.Rock -> {

				}

				RockPaperViewModel.RockPaperState.Paper -> {

				}

				RockPaperViewModel.RockPaperState.Scissors -> {

				}

				RockPaperViewModel.RockPaperState.winOrLoss -> {

				}
			}
		}
	}
}