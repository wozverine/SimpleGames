package com.glitch.simplegames.ui.guessthenumber

import android.os.Bundle
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
            btnStartGame.setOnClickListener(){
                viewModel.startGame()
                gamePlay()
            }
        }
    }

    private fun gamePlay() = with(binding) {
        viewModel.playGuessTheNumberState.observe(viewLifecycleOwner) { state ->
            when (state) {
                GuessTheNumberViewModel.PlayGuessTheNumberState.Loading -> {
                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    progressBar.visible()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.SaveState -> {
                    progressBar.gone()
                    //productAdapter.submitList(state.products)
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.EmptyScreen -> {
                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnStartGame.gone()

                    progressBar.gone()
                    tvEmpty.text = state.failMessage
                    tvEmpty.visible()
                    ivEmpty.visible()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.ShowMessage -> {
                    tvNumberActual.gone()
                    tilNumber.gone()
                    tietNumber.gone()
                    btnStartGame.gone()

                    progressBar.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.GamingState -> {
                    tvNumberActual.visible()
                    tilNumber.visible()
                    tietNumber.visible()
                    btnStartGame.gone()

                    progressBar.gone()
                }

                is GuessTheNumberViewModel.PlayGuessTheNumberState.IsWonScreen -> {
                    //findNavController().navigate(R.id.action_homeFragment_to_signInFragment)
                    progressBar.gone()
                }

            }
        }
    }
}

