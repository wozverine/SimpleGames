package com.glitch.simplegames.ui.guessthenumber

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.common.Resource
import com.glitch.simplegames.data.model.response.ScoreUI
import com.glitch.simplegames.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class GuessTheNumberViewModel @Inject constructor(
	private val scoreRepository: ScoreRepository
) : ViewModel() {
	private var _playGuessTheNumberState = MutableLiveData<PlayGuessTheNumberState>()
	val playGuessTheNumberState: LiveData<PlayGuessTheNumberState> get() = _playGuessTheNumberState


	fun openPage() = viewModelScope.launch {
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		try {
			Log.d("ViewModel", "Before getScores()")
			val result = scoreRepository.getScores()
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
		try {
			_playGuessTheNumberState.value = PlayGuessTheNumberState.GamingState(true)
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

	/*fun wonGame() = viewModelScope.launch {
		_playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

		_playGuessTheNumberState.value = when (val result = scoreRepository.getScores()) {
			is Resource.GamingState -> PlayGuessTheNumberState.GamingState(true)
			is Resource.IsWonScreen -> PlayGuessTheNumberState.IsWonScreen(true)
			is Resource.Error -> PlayGuessTheNumberState.ShowMessage(result.errorMessage)
			is Resource.Fail -> PlayGuessTheNumberState.EmptyScreen(result.failMessage)
			is Resource.SaveScore -> PlayGuessTheNumberState.SaveState(result.data)
		}

	}*/

	sealed interface PlayGuessTheNumberState {
		object Loading : PlayGuessTheNumberState
		data class GamingState(val gaming: Boolean) : PlayGuessTheNumberState
		data class WaitingState(val waiting: Boolean) : PlayGuessTheNumberState
		data class IsWonScreen(val won: Boolean) : PlayGuessTheNumberState
		data class ShowMessage(val errorMessage: String) : PlayGuessTheNumberState
		data class SaveState(val scores: List<ScoreUI>) : PlayGuessTheNumberState
		data class EmptyScreen(val failMessage: String) : PlayGuessTheNumberState
	}

}