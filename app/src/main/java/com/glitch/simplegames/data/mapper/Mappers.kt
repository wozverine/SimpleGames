package com.glitch.simplegames.data.mapper

import com.glitch.simplegames.data.model.response.Score
import com.glitch.simplegames.data.model.response.ScoreEntity
import com.glitch.simplegames.data.model.response.ScoreUI

fun Score.mapToProductUI(favorites: List<Int>) = ScoreUI(
    gameId = gameId ?: 1,
    username = username.orEmpty(),
    score = score ?: 0
)

fun List<Score>.mapScoreToScoreUI(favorites: List<Int>) = map {
    ScoreUI(
        gameId = it.gameId ?: 1,
        username = it.username.orEmpty(),
        score = it.score ?: 0
    )
}

fun ScoreUI.mapToScoreEntity() = ScoreEntity(
    gameId = gameId,
    username = username,
    score = score
)

fun List<ScoreEntity>.mapScoreEntityToScoreUI() = map {
    ScoreUI(
        gameId = it.gameId ?: 1,
        username = it.username.orEmpty(),
        score = it.score ?: 0
    )
}
