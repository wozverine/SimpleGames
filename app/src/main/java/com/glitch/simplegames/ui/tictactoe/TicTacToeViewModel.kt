package com.glitch.simplegames.ui.tictactoe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
) : ViewModel() {
	private var _playTicTacToeState = MutableLiveData<PlayTicTacToeState>()
	val playTicTacToeState: LiveData<PlayTicTacToeState> get() = _playTicTacToeState

	fun checkStart() = viewModelScope.launch {
		val isItX: Boolean = generateXO()
		_playTicTacToeState.value = PlayTicTacToeState.StartState(isItX)
	}

	fun firstTurn(isItX: Boolean) = viewModelScope.launch {
		if (isItX) {
			_playTicTacToeState.value = PlayTicTacToeState.GamingState
		} else {
			_playTicTacToeState.value = PlayTicTacToeState.WaitingState
		}
	}

	fun playerPlayed() = viewModelScope.launch {
		_playTicTacToeState.value = PlayTicTacToeState.WaitingState
		Log.v("TicTacToeViewModel", "playerPlayed")
	}

	fun pcPlayed() = viewModelScope.launch {
		_playTicTacToeState.value = PlayTicTacToeState.GamingState
		Log.v("TicTacToeViewModel", "pcPlayed")
	}

	private fun generateXO(): Boolean {
		return Random.nextBoolean()
	}

	sealed interface PlayTicTacToeState {
		data object Loading : PlayTicTacToeState
		data class StartState(val isItX: Boolean) : PlayTicTacToeState
		data object GamingState : PlayTicTacToeState
		data object WaitingState : PlayTicTacToeState

		/*data class GamingState(val isItX: Boolean) : PlayTicTacToeState
		data class WaitingState(val waiting: Boolean) : PlayTicTacToeState*/


		data class IsWonScreen(val guessLeft: Int) : PlayTicTacToeState

		data class ShowMessage(val errorMessage: String) : PlayTicTacToeState

		data class EmptyScreen(val failMessage: String) : PlayTicTacToeState
	}
}