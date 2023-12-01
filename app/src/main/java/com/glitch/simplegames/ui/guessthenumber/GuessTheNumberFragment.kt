package com.glitch.simplegames.ui.guessthenumber

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.glitch.simplegames.R
import com.glitch.simplegames.common.gone
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.common.visible
import com.glitch.simplegames.databinding.FragmentGuessthenumberBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuessTheNumberFragment : Fragment(R.layout.fragment_guessthenumber) {
	private val binding by viewBinding(FragmentGuessthenumberBinding::bind)

	private val viewModel by viewModels<GuessTheNumberViewModel>()

	private var secretNumber = 0
	private var guessCount = 10

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewModel.openPage()
		gamePlay()
		with(binding) {
			btnStartGame.setOnClickListener {
				viewModel.startGame()
				//tvNumberActual.text = secretNumber.toString()
				tvGuessLeft.text = buildString {
					append(getString(R.string.guess_left))
					append(guessCount)
				}
			}

			btnSubmit.setOnClickListener {
				guessCount -= 1

				tvHintText.text = when {
					tietNumber.text.toString()
						.toInt() == secretNumber -> getString(R.string.congrats)

					secretNumber > tietNumber.text.toString().toInt() -> getString(R.string.too_low)
					secretNumber < tietNumber.text.toString()
						.toInt() -> getString(R.string.too_high)

					else -> {
						getString(R.string.guess_the_number)
					}
				}

				if (secretNumber.toString() == tietNumber.text.toString()) {
					guessCount += 1
					viewModel.wonGame()
					//btnSubmit.text = "Start again"
				}

				val txt = buildString {
					append(getString(R.string.guess_left))
					append(guessCount)
				}
				tvGuessLeft.text = txt
				tietNumber.text = null
			}

			btnPlayAgain.setOnClickListener {
				guessCount = 10
				viewModel.startGame()
				tvHintText.text = getString(R.string.guess_the_number)
				//tvNumberActual.text = secretNumber.toString()

				val txt = buildString {
					append(getString(R.string.guess_left))
					append(guessCount)
				}
				tvGuessLeft.text = txt
			}
		}
	}

	private fun gamePlay() = with(binding) {
		viewModel.playGuessTheNumberState.observe(viewLifecycleOwner) { state ->
			when (state) {
				GuessTheNumberViewModel.PlayGuessTheNumberState.Loading -> {
					Log.d("Fragment", "Loading State: $state")
					Log.d("Fragment", "A: Loading")
					playLayout.gone()
					btnSubmit.gone()
					btnPlayAgain.gone()
					btnStartGame.gone()

					progressBar.visible()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.SaveState -> {
					Log.d("Fragment", "Save State: ${state.scores}")
					Log.d("Fragment", "A: SaveState")
					//TODO
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.GamingState -> {
					Log.d("Fragment", "Gaming State: ${state.gaming}")
					Log.d("Fragment", "A: GamingState")
					playLayout.visible()
					tilNumber.visible()
					btnSubmit.visible()
					btnPlayAgain.gone()
					btnStartGame.gone()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()

					secretNumber = state.secretNumber
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.IsWonScreen -> {
					Log.d("Fragment", "IsWon State: ${state.guessLeft}")
					Log.d("Fragment", "A: IsWonScreen")
					//findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
					//guessCount = state.guessLeft

					playLayout.visible()
					tilNumber.gone()
					btnSubmit.gone()
					btnStartGame.gone()
					btnPlayAgain.visible()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.WaitingState -> {
					Log.d("Fragment", "Waiting State: ${state.waiting}")
					Log.d("Fragment", "A: WaitingState")
					playLayout.gone()
					btnStartGame.visible()
					btnSubmit.gone()
					btnPlayAgain.gone()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.EmptyScreen -> {
					Log.d("Fragment", "Empty Screen State: ${state.failMessage}")
					Log.d("Fragment", "A: EmptyScreen")
					playLayout.gone()
					btnSubmit.gone()
					btnPlayAgain.gone()
					btnStartGame.gone()

					progressBar.gone()
					tvEmpty.text = state.failMessage
					tvEmpty.visible()
					ivEmpty.visible()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.ShowMessage -> {
					Log.d("Fragment", "Show Message State: ${state.errorMessage}")
					Log.d("Fragment", "A: ShowMessage")
					playLayout.gone()
					btnPlayAgain.gone()
					btnSubmit.gone()
					btnStartGame.gone()

					progressBar.gone()
					Snackbar.make(requireView(), state.errorMessage, 1000).show()
				}
			}
		}
	}
}

