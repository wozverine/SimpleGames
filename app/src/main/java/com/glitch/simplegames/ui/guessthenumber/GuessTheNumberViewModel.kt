package com.glitch.simplegames.ui.guessthenumber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GuessTheNumberViewModel @Inject constructor(
	private val gameRepository: GameRepository
) : ViewModel() {
	private var _playGuessTheNumberState = MutableLiveData<PlayGuessTheNumberState>()
	val playGuessTheNumberState: LiveData<PlayGuessTheNumberState> get() = _playGuessTheNumberState


	fun openPage() = viewModelScope.launch {
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		try {
			Log.d("ViewModel", "Before getScores()")
			val result = gameRepository.getHighscoreForGame(1)
			Log.d("ViewModel repo", result.toString())

			_playGuessTheNumberState.value = PlayGuessTheNumberState.WaitingState(true)
		} catch (e: Exception) {
			Log.d("ViewModel", "Exception in startGame(): ${e.message}")
			_playGuessTheNumberState.value =
				PlayGuessTheNumberState.EmptyScreen("An error occurred")
		}
	}

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

	/*fun startGame() = viewModelScope.launch {
		Log.d("ViewModel", "Before getScores()")
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		try {
			val result = scoreRepository.getScores()
			Log.d("ViewModel repo", result.toString())

			withContext(Dispatchers.Main) {
				_playGuessTheNumberState.value = when (result) {

					is Resource.GamingState -> {
						Log.d("ViewModel", "Gaming State")
						PlayGuessTheNumberState.GamingState(true)
					}

					is Resource.IsWonScreen -> PlayGuessTheNumberState.IsWonScreen(false)
					is Resource.Error -> PlayGuessTheNumberState.ShowMessage(result.errorMessage)
					is Resource.Fail -> PlayGuessTheNumberState.EmptyScreen(result.failMessage)
					is Resource.SaveScore -> {
						Log.d("ViewModel", "SaveScore State")
						if (result.data.isEmpty()) {
							PlayGuessTheNumberState.GamingState(true)
						} else {
							PlayGuessTheNumberState.SaveState(result.data)
						}
					}
					else -> {
						// Handle unexpected result
						Log.d("ViewModel", "Unexpected result: $result")
						PlayGuessTheNumberState.EmptyScreen("An unexpected result occurred")
					}
				}
			}
		} catch (e: Exception) {
			Log.d("ViewModel", "Exception in startGame(): ${e.message}")
			_playGuessTheNumberState.value =
				PlayGuessTheNumberState.EmptyScreen("An error occurred")
		}
	}*/

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

	private fun generateSecretNumber(): Int {
		return (1..99).random()
	}

	sealed interface PlayGuessTheNumberState {
		object Loading : PlayGuessTheNumberState
		data class GamingState(val gaming: Boolean, val secretNumber: Int) : PlayGuessTheNumberState
		data class WaitingState(val waiting: Boolean) : PlayGuessTheNumberState
		data class IsWonScreen(val guessLeft: Int) : PlayGuessTheNumberState
		data class ShowMessage(val errorMessage: String) : PlayGuessTheNumberState
		data class EmptyScreen(val failMessage: String) : PlayGuessTheNumberState
	}

}