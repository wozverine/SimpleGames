package com.glitch.simplegames.ui.selectgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glitch.simplegames.data.repository.ScoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SelectGameViewModel @Inject constructor(
    private val scoreRepository: ScoreRepository
) : ViewModel() {

    private var _selectGameState = MutableLiveData<SelectGameState>()
    val selectGameState: LiveData<SelectGameState> get() = _selectGameState

    fun selectGame() = viewModelScope.launch {
        scoreRepository.getScores()

        _selectGameState.value = SelectGameState.GoToGame
    }


    sealed interface SelectGameState {
        object GoToGame : SelectGameState
    }
}