package com.glitch.simplegames.ui.tictactoe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.ui.guessthenumber.GuessTheNumberViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicTacToeViewModel @Inject constructor(

): ViewModel() {
    private var _playTicTacToeState = MutableLiveData<PlayTicTactoeState>()
    val playGuessTheNumberState: LiveData<PlayTicTactoeState> get() = _playTicTacToeState

    fun playGame() = viewModelScope.launch {
        /*_selectGameState.value = when (val result = scoreRepository.getScores()) {
            is Resource.Success -> SelectGameState.SuccessState(result.data)
            is Resource.Fail -> SelectGameState.EmptyScreen(result.failMessage)
            is Resource.Error -> SelectGameState.ShowMessage(result.errorMessage)
        }*/
    }

    sealed interface PlayTicTactoeState {
        object PlayTheGame : PlayTicTactoeState
        /*data class SuccessState(val products: List<ScoreUI>) : SelectGameState
        data class EmptyScreen(val failMessage: String) : SelectGameState
        data class ShowMessage(val errorMessage: String) : SelectGameState*/
    }
}