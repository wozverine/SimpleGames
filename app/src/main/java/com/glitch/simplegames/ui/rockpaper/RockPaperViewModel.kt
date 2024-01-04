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
		_rockPaperState.value = computerChoice
		return when {
			playerChoice == computerChoice -> "D"
			playerChoice is RockPaperState.Rock && computerChoice is RockPaperState.Scissors ||
					playerChoice is RockPaperState.Rock && computerChoice is RockPaperState.Lizard ||
					playerChoice is RockPaperState.Scissors && computerChoice is RockPaperState.Paper ||
					playerChoice is RockPaperState.Scissors && computerChoice is RockPaperState.Lizard ||
					playerChoice is RockPaperState.Lizard && computerChoice is RockPaperState.Spock ||
					playerChoice is RockPaperState.Lizard && computerChoice is RockPaperState.Paper ||
					playerChoice is RockPaperState.Spock && computerChoice is RockPaperState.Scissors ||
					playerChoice is RockPaperState.Spock && computerChoice is RockPaperState.Rock ||
					playerChoice is RockPaperState.Paper && computerChoice is RockPaperState.Rock ||
					playerChoice is RockPaperState.Paper && computerChoice is RockPaperState.Spock ->
				"P"

			else -> "PC"
		}
	}

	fun generateComputerChoice(): RockPaperState {
		return when ((0..4).random()) {
			0 -> RockPaperState.Rock
			1 -> RockPaperState.Paper
			2 -> RockPaperState.Scissors
			3 -> RockPaperState.Lizard
			else -> RockPaperState.Spock
		}
	}

	sealed interface RockPaperState {
		data object Rock : RockPaperState
		data object Paper : RockPaperState
		data object Scissors : RockPaperState
		data object Lizard : RockPaperState
		data object Spock : RockPaperState
	}
}