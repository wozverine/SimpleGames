package com.glitch.simplegames.ui.rockpaper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RockPaperViewModel @Inject constructor(
) : ViewModel() {
	private val _rockPaperState = MutableLiveData<RockPaperState>()
	val rockPaperState: LiveData<RockPaperState> get() = _rockPaperState

	fun playGame(playerChoice: RockPaperState, computerChoice: RockPaperState): String {
		return when {
			playerChoice == computerChoice -> "It's a tie!"
			playerChoice is RockPaperState.Rock && computerChoice is RockPaperState.Scissors ||
					playerChoice is RockPaperState.Paper && computerChoice is RockPaperState.Rock ||
					playerChoice is RockPaperState.Scissors && computerChoice is RockPaperState.Paper ->
				"You win!"

			else -> "Computer wins!"
		}
	}

	fun generateComputerChoice(): RockPaperState {
		return when ((0..2).random()) {
			0 -> RockPaperState.Rock
			1 -> RockPaperState.Paper
			else -> RockPaperState.Scissors
		}
	}

	sealed interface RockPaperState {
		data object Loading : RockPaperState
		data object Rock : RockPaperState
		data object Paper : RockPaperState
		data object Scissors : RockPaperState
		data object winOrLoss : RockPaperState
	}
}