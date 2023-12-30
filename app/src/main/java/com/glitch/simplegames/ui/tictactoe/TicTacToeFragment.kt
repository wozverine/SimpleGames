package com.glitch.simplegames.ui.tictactoe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.glitch.simplegames.R
import com.glitch.simplegames.common.gone
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.common.visible
import com.glitch.simplegames.databinding.FragmentTictactoeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicTacToeFragment : Fragment(R.layout.fragment_tictactoe) {
	private val binding by viewBinding(FragmentTictactoeBinding::bind)
	private val viewModel by viewModels<TicTacToeViewModel>()
	private var isHumanX: Boolean = true
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.checkStart()
		gamePlay()

		initializeButtonListeners()
		with(binding) {
			btnPlayAgain.setOnClickListener {
				resetBoard()
				viewModel.checkStart()
			}
		}
	}

	private fun initializeButtonListeners() {
		with(binding) {
			setButtonClickListener(imgBtn1, 0, 0)
			setButtonClickListener(imgBtn2, 0, 1)
			setButtonClickListener(imgBtn3, 0, 2)
			setButtonClickListener(imgBtn4, 1, 0)
			setButtonClickListener(imgBtn5, 1, 1)
			setButtonClickListener(imgBtn6, 1, 2)
			setButtonClickListener(imgBtn7, 2, 0)
			setButtonClickListener(imgBtn8, 2, 1)
			setButtonClickListener(imgBtn9, 2, 2)
		}
	}

	private fun setButtonClickListener(button: ImageButton, row: Int, col: Int) {
		button.setOnClickListener {
			if (button.tag == "placeholder") {
				button.setImageResource(R.drawable.letter_x_64px)
				button.tag = "x"
				viewModel.playerMove(row, col)
			}
		}
	}

	private fun updateBoardForPCMove(row: Int, col: Int) {
		Log.v("TicTacToeFragment", "updateBoardForPCMoveStart")
		// Get the corresponding ImageButton for the PC move and update its drawable
		val button = when (row) {
			0 -> when (col) {
				0 -> binding.imgBtn1
				1 -> binding.imgBtn2
				else -> binding.imgBtn3
			}

			1 -> when (col) {
				0 -> binding.imgBtn4
				1 -> binding.imgBtn5
				else -> binding.imgBtn6
			}

			else -> when (col) {
				0 -> binding.imgBtn7
				1 -> binding.imgBtn8
				else -> binding.imgBtn9
			}
		}
		button.setImageResource(R.drawable.letter_o_64px)
		if (isHumanX) {
			button.tag = "O"
		} else {
			button.tag = "X"
		}
		viewModel.checkGameResult(isItHuman = false)
		Log.v("TicTacToeFragment", "updateBoardForPCMove - checkGameResult")
	}


	private fun resetBoard() = with(binding) {
		layoutGrid.isVisible = true
		layoutPlaying.isVisible = true
		layoutEnd.isVisible = false
		Log.v("TicTacToeFragment", "resetBoard")
		imgBtn1.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn2.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn3.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn4.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn5.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn6.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn7.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn8.setImageResource(R.drawable.icons8_octahedron_64)
		imgBtn9.setImageResource(R.drawable.icons8_octahedron_64)

		imgBtn1.tag = "placeholder"
		imgBtn2.tag = "placeholder"
		imgBtn3.tag = "placeholder"
		imgBtn4.tag = "placeholder"
		imgBtn5.tag = "placeholder"
		imgBtn6.tag = "placeholder"
		imgBtn7.tag = "placeholder"
		imgBtn8.tag = "placeholder"
		imgBtn9.tag = "placeholder"
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
					layoutGrid.gone()
					layoutPlaying.gone()
					layoutEnd.gone()
					progressBar.visible()
					tvEmpty.gone()
					ivEmpty.gone()
					Log.v("TicTacToeFragment", "Observed state: $state")
				}

				is TicTacToeViewModel.PlayTicTacToeState.StartState -> {
					Log.v("TicTacToeFragment", "Observed state: $state")
					layoutGrid.isVisible = true
					layoutPlaying.isVisible = true
					layoutEnd.isVisible = false
					progressBar.isVisible = false
					tvEmpty.isVisible = false
					ivEmpty.isVisible = false
					isHumanX = state.isItX
					btnControl(false)
					viewModel.firstTurn(isHumanX)
					if (isHumanX) {
						ivO.isVisible = false
						ivX.isVisible = true
					}
				}

				TicTacToeViewModel.PlayTicTacToeState.PlayerMoveState -> {
					layoutGrid.isVisible = true
					layoutEnd.isVisible = false
					layoutPlaying.isVisible = true
					progressBar.isVisible = false
					tvEmpty.isVisible = false
					ivEmpty.isVisible = false
					Log.v("TicTacToeFragment", "Observed state: $state")
					tvTurn.text = getString(R.string.your_turn)
					btnControl(true)
				}

				is TicTacToeViewModel.PlayTicTacToeState.PCMoveState -> {
					layoutGrid.isVisible = true
					layoutEnd.isVisible = false
					layoutPlaying.isVisible = true
					progressBar.isVisible = false
					tvEmpty.isVisible = false
					ivEmpty.isVisible = false
					Log.v("TicTacToeFragment", "Observed state: $state")
					tvTurn.text = getString(R.string.pc_turn)
					btnControl(false)
					updateBoardForPCMove(state.row, state.column)
				}

				is TicTacToeViewModel.PlayTicTacToeState.IsWonScreen -> {
					layoutGrid.isVisible = true
					layoutEnd.isVisible = true
					layoutPlaying.isVisible = false
					progressBar.isVisible = false
					tvEmpty.isVisible = false
					ivEmpty.isVisible = false
					if (state.playerWon) {
						tvGameEnd.text = getString(R.string.congrats)
					} else {
						tvGameEnd.text = getString(R.string.lost)
					}
					Log.v("TicTacToeFragment", "Observed state: $state")
				}

				TicTacToeViewModel.PlayTicTacToeState.DrawState -> {
					layoutGrid.isVisible = true
					layoutEnd.isVisible = true
					layoutPlaying.isVisible = false
					progressBar.isVisible = false
					tvEmpty.isVisible = false
					ivEmpty.isVisible = false
					tvGameEnd.text = getString(R.string.draw)
					Log.v("TicTacToeFragment", "Observed state: $state")
				}

				else -> {
					layoutGrid.isVisible = false
					layoutEnd.isVisible = true
					layoutPlaying.isVisible = false
					progressBar.isVisible = false
					tvEmpty.isVisible = true
					ivEmpty.isVisible = true
					Log.v("TicTacToeFragment", "elseLAAAAAAAAAAA")
				}
			}
		}
	}
}