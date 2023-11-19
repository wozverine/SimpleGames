package com.glitch.simplegames.ui.home

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
class HomeViewModel @Inject constructor(
    //private val scoreRepository: ScoreRepository
) : ViewModel() {

    private var _selectGameState = MutableLiveData<SelectGameState>()
    val selectGameState: LiveData<SelectGameState> get() = _selectGameState

    fun selectGame() = viewModelScope.launch {
        /*_selectGameState.value = when (val result = scoreRepository.getScores()) {
            is Resource.Success -> SelectGameState.SuccessState(result.data)
            is Resource.Fail -> SelectGameState.EmptyScreen(result.failMessage)
            is Resource.Error -> SelectGameState.ShowMessage(result.errorMessage)
        }*/
    }


    sealed interface SelectGameState {
        object GoToGame : SelectGameState
        /*data class SuccessState(val products: List<ScoreUI>) : SelectGameState
        data class EmptyScreen(val failMessage: String) : SelectGameState
        data class ShowMessage(val errorMessage: String) : SelectGameState*/
    }
}