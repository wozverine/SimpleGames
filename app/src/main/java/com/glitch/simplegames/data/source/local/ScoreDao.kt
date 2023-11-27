package com.glitch.simplegames.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glitch.simplegames.data.model.response.Score
import com.glitch.simplegames.data.model.response.ScoreEntity

@Dao
interface ScoreDao {
    @Query("SELECT * FROM game_scores")
    suspend fun getScore(): List<ScoreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScore(scoreEntity: ScoreEntity)

    @Delete
    suspend fun deleteScores(scoreEntity: ScoreEntity)

    @Query("SELECT gameId FROM game_scores")
    suspend fun getGameIds(): List<Int>

    @Query("DELETE FROM game_scores")
    suspend fun clearScores()

    @Query("SELECT * FROM game_scores WHERE gameId = :gameId LIMIT 1")
    suspend fun getScoreForGame(gameId: Int): ScoreEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultScore(scoreEntity: ScoreEntity)
}