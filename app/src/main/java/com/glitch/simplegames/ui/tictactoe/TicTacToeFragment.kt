package com.glitch.simplegames.ui.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
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
	private var isItX: Boolean = true
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.checkStart()
		gamePlay()

		with(binding) {
			setButtonClickListener(imgBtn1)
			setButtonClickListener(imgBtn2)
			setButtonClickListener(imgBtn3)
			setButtonClickListener(imgBtn4)
			setButtonClickListener(imgBtn5)
			setButtonClickListener(imgBtn6)
			setButtonClickListener(imgBtn7)
			setButtonClickListener(imgBtn8)
			setButtonClickListener(imgBtn9)
		}
	}

	private fun setButtonClickListener(button: ImageButton) {
		button.setOnClickListener {
			if (button.tag == "star") {
				button.setImageResource(R.drawable.letter_x_64px)
				viewModel.playerPlayed()
				button.tag = "x"
			}
		}
	}

	private fun gamePlay() = with(binding) {
		fun btnControl(check: Boolean) {
			imgBtn1.isEnabled = check
			imgBtn2.isEnabled = check
			imgBtn3.isEnabled = check
			imgBtn4.isEnabled = check
			imgBtn5.isEnabled = check
			imgBtn6.isEnabled = check
			imgBtn7.isEnabled = check
			imgBtn8.isEnabled = check
			imgBtn9.isEnabled = check
		}
		viewModel.playTicTacToeState.observe(viewLifecycleOwner) { state ->
			when (state) {
				TicTacToeViewModel.PlayTicTacToeState.Loading -> {
					btnControl(false)
				}

				is TicTacToeViewModel.PlayTicTacToeState.StartState -> {
					isItX = state.isItX
					btnControl(false)
					viewModel.firstTurn(isItX)
					if (isItX) {
						ivO.isVisible = false
						ivX.isVisible = true
					}
				}

				TicTacToeViewModel.PlayTicTacToeState.GamingState -> {
					tvTurn.text = getString(R.string.your_turn)
					btnControl(true)
				}

				TicTacToeViewModel.PlayTicTacToeState.WaitingState -> {
					tvTurn.text = getString(R.string.pc_turn)
					btnControl(false)
				}

				else -> {
					Log.v("TicTacToeFragment", "Observed state: $state")
				}
			}
		}
	}
}