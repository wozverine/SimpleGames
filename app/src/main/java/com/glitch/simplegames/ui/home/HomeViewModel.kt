package com.glitch.simplegames.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.common.Resource
import com.glitch.simplegames.data.model.response.GameEntity
import com.glitch.simplegames.data.model.response.GameUI
import com.glitch.simplegames.data.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
	private val gameRepository: GameRepository
) : ViewModel() {

	private var _selectGameState = MutableLiveData<SelectGameState>()
	val selectGameState: LiveData<SelectGameState> get() = _selectGameState

	fun getGames() = viewModelScope.launch {
		_selectGameState.value = SelectGameState.Loading

		_selectGameState.value = when (val result = gameRepository.getGames()) {
			is Resource.Success -> SelectGameState.SuccessState(result.data)
			is Resource.Fail -> SelectGameState.EmptyScreen(result.failMessage)
			is Resource.Error -> SelectGameState.ShowMessage(result.errorMessage)
		}
	}

	suspend fun getHighscore(gameId: Int): Int {
		return gameRepository.getHighscoreForGame(gameId) ?: 0
	}

	suspend fun updateScore(gameEntity: GameEntity) {
		gameRepository.updateScores(gameEntity)
	}
	suspend fun addGame(gameEntity: GameEntity) {
		gameRepository.addGame(gameEntity)
	}

}
sealed interface SelectGameState {
	data object Loading : SelectGameState
	data class SuccessState(val games: List<GameUI>) : SelectGameState
	data class EmptyScreen(val failMessage: String) : SelectGameState
	data class ShowMessage(val errorMessage: String) : SelectGameState
}