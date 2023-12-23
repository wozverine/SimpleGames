package com.glitch.simplegames.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.glitch.simplegames.data.model.response.GameEntity

@Dao
interface GameDao {
	@Query("SELECT * FROM games")
	suspend fun getGames(): List<GameEntity>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addGame(gameEntity: GameEntity)

	@Delete
	suspend fun deleteGames(gameEntity: GameEntity)

	/*@Query("SELECT gameId FROM games")
	suspend fun getGameIds(): List<Int>*/

	@Query("DELETE FROM games")
	suspend fun clearScores()

	@Query("SELECT highScore FROM games WHERE gameId = :gameId")
	suspend fun getHighscoreForGame(gameId: Int): GameEntity?


	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insertDefaultScore(gameEntity: GameEntity)

	@Update
	fun updateGames(gameEntity: GameEntity)
}