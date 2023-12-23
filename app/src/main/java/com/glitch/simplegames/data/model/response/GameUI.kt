package com.glitch.simplegames.data.model.response

data class GameUI(
	val gameId: Int,
	val title: String,
	val username: String,
	val highScore: Int = 0
)
