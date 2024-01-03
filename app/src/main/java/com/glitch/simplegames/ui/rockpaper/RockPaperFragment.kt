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

		play()

		with(binding) {
			ibRock.setOnClickListener {
				ivPlayer.setImageResource(R.drawable.rock)
				val statePC = viewModel.generateComputerChoice()

				tvWin.text = viewModel.playGame(
					playerChoice = RockPaperViewModel.RockPaperState.Rock,
					computerChoice = statePC
				)

			}
			ibPaper.setOnClickListener {
				ivPlayer.setImageResource(R.drawable.paper)
				val statePC = viewModel.generateComputerChoice()

				tvWin.text = viewModel.playGame(
					playerChoice = RockPaperViewModel.RockPaperState.Paper,
					computerChoice = statePC
				)
			}
			ibScissors.setOnClickListener {
				ivPlayer.setImageResource(R.drawable.scissors)
				val statePC = viewModel.generateComputerChoice()

				tvWin.text = viewModel.playGame(
					playerChoice = RockPaperViewModel.RockPaperState.Scissors,
					computerChoice = statePC
				)
			}
			ibLizard.setOnClickListener {
				ivPlayer.setImageResource(R.drawable.lizard)
				val statePC = viewModel.generateComputerChoice()

				tvWin.text = viewModel.playGame(
					playerChoice = RockPaperViewModel.RockPaperState.Lizard,
					computerChoice = statePC
				)
			}
			ibSpock.setOnClickListener {
				ivPlayer.setImageResource(R.drawable.spock)
				val statePC = viewModel.generateComputerChoice()

				tvWin.text = viewModel.playGame(
					playerChoice = RockPaperViewModel.RockPaperState.Spock,
					computerChoice = statePC
				)
			}
		}
	}

	private fun play() = with(binding) {
		viewModel.rockPaperState.observe(viewLifecycleOwner) { state ->
			when (state) {
				RockPaperViewModel.RockPaperState.Rock -> {
					ivPc.setImageResource(R.drawable.rock)
				}

				RockPaperViewModel.RockPaperState.Paper -> {
					ivPc.setImageResource(R.drawable.paper)
				}

				RockPaperViewModel.RockPaperState.Scissors -> {
					ivPc.setImageResource(R.drawable.scissors)
				}

				RockPaperViewModel.RockPaperState.Lizard -> {
					ivPc.setImageResource(R.drawable.lizard)
				}

				RockPaperViewModel.RockPaperState.Spock -> {
					ivPc.setImageResource(R.drawable.spock)
				}
			}
		}
	}
}