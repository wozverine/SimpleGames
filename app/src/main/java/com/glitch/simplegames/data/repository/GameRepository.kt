package com.glitch.simplegames.data.repository

import com.glitch.simplegames.common.Resource
import com.glitch.simplegames.data.mapper.mapGameEntityToGameUI
import com.glitch.simplegames.data.model.response.GameEntity
import com.glitch.simplegames.data.model.response.GameUI
import com.glitch.simplegames.data.source.local.GameDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(
	private val gameDao: GameDao
) {
	suspend fun getGames(): Resource<List<GameUI>> = withContext(Dispatchers.IO) {
		try {
			val games = gameDao.getGames()
			Resource.Success(games.mapGameEntityToGameUI())
		} catch (e: Exception) {
			Resource.Error(e.message.orEmpty())
		}
	}

	suspend fun getHighscoreForGame(gameId: Int): Int? = withContext(Dispatchers.IO) {
		gameDao.getHighscoreForGame(gameId)?.highScore
	}

	suspend fun clearScores() = withContext(Dispatchers.IO) {
		gameDao.clearScores()
	}

	suspend fun updateScores(gameEntity: GameEntity): Resource<Unit> = withContext(Dispatchers.IO) {
		try {
			val game = gameDao.getGames().find { it.gameId == gameEntity.gameId }
			if (game?.gameId != null) {
				val updatedGameEntity = game.copy(
					highScore = gameEntity.highScore
				)
				gameDao.updateGames(updatedGameEntity)
				Resource.Success(Unit)
			} else {
				Resource.Error("Game not found")
			}
		} catch (e: Exception) {
			Resource.Error("Failed to update the game: ${e.message}")
		}
	}

	suspend fun addGame(gameEntity: GameEntity) = withContext(Dispatchers.IO) {
		gameDao.addGame(gameEntity)
	}

}
