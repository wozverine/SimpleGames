package com.glitch.simplegames.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
) : ViewModel() {

	private var _splashState = MutableLiveData<SplashState>()
	val splashState: LiveData<SplashState> get() = _splashState

	init {
		viewModelScope.launch {
			delay(2000)
			checkScore()
		}
	}

	private fun checkScore() = viewModelScope.launch {
		_splashState.value = SplashState.GoToHome
	}
}

sealed interface SplashState {
	data object GoToHome : SplashState
}