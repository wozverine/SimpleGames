package com.glitch.simplegames.ui.guessthenumber

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.glitch.simplegames.R
import com.glitch.simplegames.common.gone
import com.glitch.simplegames.common.viewBinding
import com.glitch.simplegames.common.visible
import com.glitch.simplegames.data.model.response.GameEntity
import com.glitch.simplegames.data.repository.GameRepository
import com.glitch.simplegames.data.source.local.GameRoomDB
import com.glitch.simplegames.databinding.FragmentGuessBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GuessTheNumberFragment : Fragment(R.layout.fragment_guess) {
	private val binding by viewBinding(FragmentGuessBinding::bind)

	private val viewModel by viewModels<GuessTheNumberViewModel>()

	private lateinit var gameRepository: GameRepository

	private var secretNumber = 0
	private var guessCount = 10

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val gameDao = GameRoomDB.getInstance(requireContext()).gameDao()
		gameRepository = GameRepository(gameDao)

		viewModel.startGame()
		gamePlay()
		with(binding) {
			btnSubmit.setOnClickListener {
				if (tietNumber.text?.isNotEmpty() == true) {
					guessCount -= 1

					tvHint.text = when {
						tietNumber.text.toString()
							.toInt() == secretNumber -> getString(R.string.congrats)

						secretNumber > tietNumber.text.toString()
							.toInt() -> getString(R.string.too_low)

						secretNumber < tietNumber.text.toString()
							.toInt() -> getString(R.string.too_high)

						else -> {
							getString(R.string.guess_the_number)
						}
					}

					if (secretNumber.toString() == tietNumber.text.toString()) {
						guessCount += 1

						viewModel.wonGame()
						lifecycleScope.launch {
							if (guessCount * 10 > gameRepository.getHighscoreForGame(1)!!) {
								val newScore = GameEntity(
									1, getString(R.string.guess_the_number), "q", guessCount * 10
								)
								gameRepository.updateScores(newScore)
							}
						}
						tvScore.text = buildString {
							append(getString(R.string.score))
							append((guessCount * 10).toString())
						}
						tvNumberActual.text = secretNumber.toString()
					}

					val txt = buildString {
						append(getString(R.string.guess_left))
						append(guessCount)
					}
					tvGuessLeft.text = txt
					tietNumber.text = null
					if (guessCount == 0) {
						viewModel.lostGame()
					}
				} else {
					Toast.makeText(requireContext(), "Please enter a number", Toast.LENGTH_SHORT)
						.show()
				}
			}

			btnPlayAgain.setOnClickListener {
				guessCount = 10
				viewModel.startGame()
				tvHint.text = getString(R.string.guess_the_number)

				val txt = buildString {
					append(getString(R.string.guess_left))
					append(guessCount)
				}
				tvGuessLeft.text = txt
				tvNumberActual.text = getString(R.string.question_mark)
			}

			tietNumber.setOnEditorActionListener { _, actionId, _ ->
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					if (tietNumber.text?.isNotEmpty() == true) {
						guessCount -= 1

						tvHint.text = when {
							tietNumber.text.toString()
								.toInt() == secretNumber -> getString(R.string.congrats)

							secretNumber > tietNumber.text.toString()
								.toInt() -> getString(R.string.too_low)

							secretNumber < tietNumber.text.toString()
								.toInt() -> getString(R.string.too_high)

							else -> {
								getString(R.string.guess_the_number)
							}
						}

						if (secretNumber.toString() == tietNumber.text.toString()) {
							guessCount += 1

							viewModel.wonGame()
							lifecycleScope.launch {
								if (guessCount * 10 > gameRepository.getHighscoreForGame(1)!!) {
									val newScore = GameEntity(
										1,
										getString(R.string.guess_the_number),
										"q",
										guessCount * 10
									)
									gameRepository.updateScores(newScore)
								}
							}
							tvScore.text = buildString {
								append(getString(R.string.score))
								append((guessCount * 10).toString())
							}
							tvNumberActual.text = secretNumber.toString()
						}

						val txt = buildString {
							append(getString(R.string.guess_left))
							append(guessCount)
						}
						tvGuessLeft.text = txt
						tietNumber.text = null
						if (guessCount == 0) {
							viewModel.lostGame()
						}
					} else {
						Toast.makeText(
							requireContext(), "Please enter a number", Toast.LENGTH_SHORT
						).show()
					}
					true
				} else false
			}
		}
	}

	private fun View.hideKeyboard() {
		val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		imm.hideSoftInputFromWindow(windowToken, 0)
	}

	private fun gamePlay() = with(binding) {
		viewModel.playGuessTheNumberState.observe(viewLifecycleOwner) { state ->
			when (state) {
				GuessTheNumberViewModel.PlayGuessTheNumberState.Loading -> {
					playLayout.gone()
					btnSubmit.gone()
					againLayout.gone()

					progressBar.visible()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.GamingState -> {
					playLayout.visible()
					tilNumber.visible()
					btnSubmit.visible()
					againLayout.gone()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()

					secretNumber = state.secretNumber
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.IsWonScreen -> {
					playLayout.visible()
					tilNumber.gone()
					btnSubmit.gone()
					againLayout.visible()
					tietNumber.hideKeyboard()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.IsLostScreen -> {
					playLayout.visible()
					tilNumber.gone()
					btnSubmit.gone()
					againLayout.visible()
					tietNumber.hideKeyboard()
					tvScore.text = getString(R.string.lost)
					tvNumberActual.text = secretNumber.toString()

					progressBar.gone()
					tvEmpty.gone()
					ivEmpty.gone()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.EmptyScreen -> {
					playLayout.gone()
					btnSubmit.gone()
					againLayout.gone()

					progressBar.gone()
					tvEmpty.text = state.failMessage
					tvEmpty.visible()
					ivEmpty.visible()
				}

				is GuessTheNumberViewModel.PlayGuessTheNumberState.ShowMessage -> {
					playLayout.gone()
					againLayout.gone()
					btnSubmit.gone()

					progressBar.gone()
					Snackbar.make(requireView(), state.errorMessage, 1000).show()
				}
			}
		}
	}
}

