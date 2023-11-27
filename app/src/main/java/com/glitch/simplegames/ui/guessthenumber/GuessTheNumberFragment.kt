package com.glitch.simplegames.ui.guessthenumber

import android.os.Bundle
import android.util.Log
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.openPage()
        gamePlay()
        with(binding) {
            btnStartGame.setOnClickListener {
                viewModel.startGame()
                Snackbar.make(requireView(), getString(R.string.guess_the_number), 1000).show()
            }
        }
    }

    private fun gamePlay() = with(binding) {
        Log.d("Fragment", "fun gamePlay")
        viewModel.playGuessTheNumberState.observe(viewLifecycleOwner) { state ->
            when (state) {
                GuessTheNumberViewModel.PlayGuessTheNumberState.Loading -> {
                    Log.d("Fragment", "Loading State: $state")
                    Log.d("Fragment", "A: Loading")

                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnStartGame.gone()

                    progressBar.visible()
                    tvEmpty.gone()
                    ivEmpty.gone()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.SaveState -> {
                    Log.d("Fragment", "Save State: ${state.scores}")
                    Log.d("Fragment", "A: SaveState")
                    progressBar.gone()
                    //TODO
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.EmptyScreen -> {
                    Log.d("Fragment", "Empty Screen State: ${state.failMessage}")
                    Log.d("Fragment", "A: EmptyScreen")
                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnStartGame.gone()
                    btnSubmit.gone()

                    progressBar.gone()
                    tvEmpty.text = state.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.ShowMessage -> {
                    Log.d("Fragment", "Show Message State: ${state.errorMessage}")
                    Log.d("Fragment", "A: ShowMessage")
                    btnSubmit.gone()
                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnStartGame.gone()

                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.GamingState -> {
                    Log.d("Fragment", "Gaming State: ${state.gaming}")
                    Log.d("Fragment", "A: GamingState")
                    btnSubmit.visible()
                    tvNumberActual.visible()
                    tilNumber.visible()
                    tietNumber.visible()
                    btnStartGame.gone()

                    progressBar.gone()
                    tvEmpty.gone()
                    ivEmpty.gone()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.IsWonScreen -> {
                    Log.d("Fragment", "IsWon State: ${state.won}")
                    Log.d("Fragment", "A: IsWonScreen")
                    //findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                    progressBar.gone()
                    //TODO
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.WaitingState -> {
                    Log.d("Fragment", "Waiting State: ${state.waiting}")
                    Log.d("Fragment", "A: WaitingState")
                    btnStartGame.visible()
                    tvNumberActual.visible()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnSubmit.gone()

                    progressBar.gone()
                    tvEmpty.gone()
                    ivEmpty.gone()
                }
            }
        }
    }
}

