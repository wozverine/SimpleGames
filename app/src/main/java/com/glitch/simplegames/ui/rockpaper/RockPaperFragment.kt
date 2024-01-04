package com.glitch.simplegames.ui.rockpaper

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
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
			setButtonListener(ibRock, R.drawable.rock, RockPaperViewModel.RockPaperState.Rock)
			setButtonListener(ibPaper, R.drawable.paper, RockPaperViewModel.RockPaperState.Paper)
			setButtonListener(
				ibScissors, R.drawable.scissors, RockPaperViewModel.RockPaperState.Scissors
			)
			setButtonListener(ibLizard, R.drawable.lizard, RockPaperViewModel.RockPaperState.Lizard)
			setButtonListener(ibSpock, R.drawable.spock, RockPaperViewModel.RockPaperState.Spock)
		}
	}

	private fun setButtonListener(
		ib: ImageButton, drawable: Int, state: RockPaperViewModel.RockPaperState
	) {
		ib.setOnClickListener {
			with(binding) {
				ivPlayer.setImageResource(drawable)
				val statePC = viewModel.generateComputerChoice()
				tvWin.text =
					when (viewModel.playGame(playerChoice = state, computerChoice = statePC)) {
						"PC" -> getString(R.string.pc_win)
						"P" -> getString(R.string.player_win)
						else -> getString(R.string.draw)
					}
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