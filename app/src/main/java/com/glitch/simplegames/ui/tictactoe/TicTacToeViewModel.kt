package com.glitch.simplegames.ui.tictactoe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TicTacToeViewModel @Inject constructor(
) : ViewModel() {
	private var _playTicTacToeState = MutableLiveData<PlayTicTacToeState>()
	val playTicTacToeState: LiveData<PlayTicTacToeState> get() = _playTicTacToeState
	private var board = Array(3) { Array(3) { "" } }

	fun checkStart() = viewModelScope.launch {
		Log.v("TicTacToeViewModel", "checkStart()")
		board = Array(3) { Array(3) { "" } }
		val isHumanX: Boolean = generateXO()
		_playTicTacToeState.value = PlayTicTacToeState.StartState(isHumanX)
	}

	fun firstTurn(isHumanX: Boolean) = viewModelScope.launch {
		Log.v("TicTacToeViewModel", "firstTurn()")
		if (isHumanX) {
			_playTicTacToeState.value = PlayTicTacToeState.PlayerMoveState
		} else {
			pcMove()
		}
	}

	fun playerMove(row: Int, col: Int) = viewModelScope.launch {
		if (board[row][col].isEmpty()) {
			board[row][col] = "X"
			checkGameResult(isItHuman = true)
		}
	}

	/*private fun pcMove() = viewModelScope.launch {
		Log.v("TicTacToeViewModel", "pcMove()")
		// Simple random move for demonstration purposes
		val emptyCells = mutableListOf<Pair<Int, Int>>()
		for (i in 0 until 3) {
			for (j in 0 until 3) {
				if (board[i][j].isEmpty()) {
					emptyCells.add(Pair(i, j))
				}
			}
		}

		if (emptyCells.isNotEmpty()) {
			Log.v("TicTacToeViewModel", "pcMove()emptyCells.isNotEmpty()")
			val randomIndex = Random.nextInt(emptyCells.size)
			val (row, col) = emptyCells[randomIndex]
			board[row][col] = "O"
			_playTicTacToeState.value = PlayTicTacToeState.PCMoveState(row = row, column = col)
		}
	}*/

	private fun pcMove() = viewModelScope.launch {
		val emptyCells = board.flatten().withIndex().filter { it.value.isEmpty() }
		if (emptyCells.isNotEmpty()) {
			val randomIndex = Random.nextInt(emptyCells.size)
			val (index, _) = emptyCells[randomIndex]
			val row = index / 3
			val col = index % 3
			board[row][col] = "O"
			_playTicTacToeState.value = PlayTicTacToeState.PCMoveState(row = row, column = col)
		}
	}


	fun checkGameResult(isItHuman: Boolean) {
		Log.v("TicTacToeViewModel", "checkGameResult()")

		for (i in 0 until 3) {
			// Check rows
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0].isNotEmpty()) {
				handleWinOrDraw(isItHuman)
				return
			}

			// Check columns
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i].isNotEmpty()) {
				handleWinOrDraw(isItHuman)
				return
			}
		}

		// Check diagonals
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0].isNotEmpty()) {
			handleWinOrDraw(isItHuman)
			return
		}
		if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2].isNotEmpty()) {
			handleWinOrDraw(isItHuman)
			return
		}

		// Check for a draw
		/*if (board.all { row -> row.all { cell -> cell.isNotEmpty() } }) {
			_playTicTacToeState.value = PlayTicTacToeState.DrawState
			Log.v("TicTacToeViewModel", "draw")
			return
		}*/

		if (board.flatten().all { it.isNotEmpty() }) {
			_playTicTacToeState.value = PlayTicTacToeState.DrawState
			return
		}


		// If no win or draw, proceed with the next move
		Log.v("TicTacToeViewModel", "else")
		if (isItHuman) {
			pcMove()
			Log.v("TicTacToeViewModel", "else - turnToPc")
			return
		} else {
			_playTicTacToeState.value = PlayTicTacToeState.PlayerMoveState
			Log.v("TicTacToeViewModel", "else - turnToPlayer")
		}
	}


	/*private fun handleWinOrDraw(isItHuman: Boolean) {
		Log.v("TicTacToeViewModel", "handleWinOrDraw()")
		Log.v("TicTacToeViewModel", isItHuman.toString())
		when (isItHuman) {
			true -> _playTicTacToeState.value =
				PlayTicTacToeState.IsWonScreen(playerWon = true)

			false -> _playTicTacToeState.value =
				PlayTicTacToeState.IsWonScreen(playerWon = false)
		}
	}*/

	private fun handleWinOrDraw(isItHuman: Boolean) {
		_playTicTacToeState.value = PlayTicTacToeState.IsWonScreen(isItHuman)
	}


	private fun generateXO(): Boolean {
		return Random.nextBoolean()
	}

	sealed interface PlayTicTacToeState {
		data object Loading : PlayTicTacToeState
		data class StartState(val isItX: Boolean) : PlayTicTacToeState
		data object PlayerMoveState : PlayTicTacToeState
		data object DrawState : PlayTicTacToeState
		data class PCMoveState(val row: Int, val column: Int) : PlayTicTacToeState

		data class IsWonScreen(val playerWon: Boolean) : PlayTicTacToeState

		data class ShowMessage(val errorMessage: String) : PlayTicTacToeState

		data class EmptyScreen(val failMessage: String) : PlayTicTacToeState
	}
}