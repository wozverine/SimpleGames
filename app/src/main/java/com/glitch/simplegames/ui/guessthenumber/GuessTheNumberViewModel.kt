package com.glitch.simplegames.ui.guessthenumber

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

    fun playGame() = viewModelScope.launch {
        /*_selectGameState.value = when (val result = scoreRepository.getScores()) {
            is Resource.Success -> SelectGameState.SuccessState(result.data)
            is Resource.Fail -> SelectGameState.EmptyScreen(result.failMessage)
            is Resource.Error -> SelectGameState.ShowMessage(result.errorMessage)
        }*/
    }

    sealed interface PlayGuessTheNumberState {
        object PlayTheGame : PlayGuessTheNumberState
        /*data class SuccessState(val products: List<ScoreUI>) : SelectGameState
        data class EmptyScreen(val failMessage: String) : SelectGameState
        data class ShowMessage(val errorMessage: String) : SelectGameState*/
    }
}