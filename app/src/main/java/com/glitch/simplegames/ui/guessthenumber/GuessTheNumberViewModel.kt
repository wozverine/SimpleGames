package com.glitch.simplegames.ui.guessthenumber

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.common.Resource
import com.glitch.simplegames.data.model.response.ScoreUI
import com.glitch.simplegames.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GuessTheNumberViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {
    private var _playGuessTheNumberState = MutableLiveData<PlayGuessTheNumberState>()
    val playGuessTheNumberState: LiveData<PlayGuessTheNumberState> get() = _playGuessTheNumberState

    /*private val isWon: Boolean = false
    private val gameWin: Boolean = false*/

    fun openPage() = viewModelScope.launch {
        _playGuessTheNumberState.value = PlayGuessTheNumberState.Loading
    }
    fun startGame() = viewModelScope.launch {
        _playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

        _playGuessTheNumberState.value = when (val result = scoreRepository.getScores()) {
            is Resource.GamingState -> PlayGuessTheNumberState.GamingState(true)
            is Resource.IsWonScreen -> PlayGuessTheNumberState.IsWonScreen(false)
            is Resource.Error -> PlayGuessTheNumberState.ShowMessage(result.errorMessage)
            is Resource.Fail -> PlayGuessTheNumberState.EmptyScreen(result.failMessage)
            is Resource.SaveScore -> PlayGuessTheNumberState.SaveState(result.data)
        }
    }

    fun wonGame() = viewModelScope.launch {
        _playGuessTheNumberState.value = PlayGuessTheNumberState.Loading

        _playGuessTheNumberState.value = when (val result = scoreRepository.getScores()) {
            is Resource.GamingState -> PlayGuessTheNumberState.GamingState(true)
            is Resource.IsWonScreen -> PlayGuessTheNumberState.IsWonScreen(true)
            is Resource.Error -> PlayGuessTheNumberState.ShowMessage(result.errorMessage)
            is Resource.Fail -> PlayGuessTheNumberState.EmptyScreen(result.failMessage)
            is Resource.SaveScore -> PlayGuessTheNumberState.SaveState(result.data)
        }

    }

    sealed interface PlayGuessTheNumberState {
        object Loading : PlayGuessTheNumberState
        data class GamingState(val gaming: Boolean) : PlayGuessTheNumberState
        data class IsWonScreen(val won: Boolean) : PlayGuessTheNumberState
        data class ShowMessage(val errorMessage: String) : PlayGuessTheNumberState
        data class SaveState(val products: List<ScoreUI>) : PlayGuessTheNumberState
        data class EmptyScreen(val failMessage: String) : PlayGuessTheNumberState
    }
}