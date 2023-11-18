package com.glitch.simplegames.data.model.response

data class ScoreUI(
    val gameId: Int,
    val username: String,
    val score: Int = 0
)
