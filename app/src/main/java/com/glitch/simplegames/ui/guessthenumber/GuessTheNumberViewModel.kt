package com.glitch.simplegames.ui.guessthenumber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuessTheNumberViewModel @Inject constructor(
) : ViewModel() {
	private var _playGuessTheNumberState = MutableLiveData<PlayGuessTheNumberState>()
	val playGuessTheNumberState: LiveData<PlayGuessTheNumberState> get() = _playGuessTheNumberState

	fun startGame() = viewModelScope.launch {
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		val secretNumber = generateSecretNumber()
		try {
			_playGuessTheNumberState.value = PlayGuessTheNumberState.GamingState(true, secretNumber)
		} catch (e: Exception) {
			Log.d("ViewModel", "Exception in startGame(): ${e.message}")
			_playGuessTheNumberState.value =
				PlayGuessTheNumberState.EmptyScreen("An error occurred")
		}
	}

	fun wonGame() = viewModelScope.launch {
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		try {
			_playGuessTheNumberState.value = PlayGuessTheNumberState.IsWonScreen(10)
		} catch (e: Exception) {
			Log.d("ViewModel", "Exception in startGame(): ${e.message}")
			_playGuessTheNumberState.value =
				PlayGuessTheNumberState.EmptyScreen("An error occurred")
		}
	}

	fun lostGame() = viewModelScope.launch {
		try {
			_playGuessTheNumberState.value = PlayGuessTheNumberState.IsLostScreen
		} catch (e: Exception) {
			Log.d("ViewModel", "Exception in lostGame(): ${e.message}")
			_playGuessTheNumberState.value =
				PlayGuessTheNumberState.EmptyScreen("An error occurred")
		}
	}

	private fun generateSecretNumber(): Int {
		return (1..99).random()
	}

	sealed interface PlayGuessTheNumberState {
		data object Loading : PlayGuessTheNumberState
		data class GamingState(val gaming: Boolean, val secretNumber: Int) : PlayGuessTheNumberState
		data class IsWonScreen(val guessLeft: Int) : PlayGuessTheNumberState
		data object IsLostScreen : PlayGuessTheNumberState
		data class ShowMessage(val errorMessage: String) : PlayGuessTheNumberState
		data class EmptyScreen(val failMessage: String) : PlayGuessTheNumberState
	}
}