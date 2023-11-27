package com.glitch.simplegames.data.repository


import com.glitch.simplegames.data.mapper.mapScoreEntityToScoreUI
import com.glitch.simplegames.common.Resource
import com.glitch.simplegames.data.model.response.ScoreEntity
import com.glitch.simplegames.data.model.response.ScoreUI
import com.glitch.simplegames.data.source.local.ScoreDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScoreRepository(
    private val scoreDao: ScoreDao
) {
    suspend fun getScores(): Resource<List<ScoreUI>> = withContext(Dispatchers.IO) {
        try {
            val games = scoreDao.getScore()
            Resource.SaveScore(games.mapScoreEntityToScoreUI())
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getScoreForGame(gameId: Int): ScoreEntity? {
        return scoreDao.getScoreForGame(gameId)
    }

    suspend fun insertDefaultScore(scoreEntity: ScoreEntity) {
        scoreDao.insertDefaultScore(scoreEntity)
    }


}
