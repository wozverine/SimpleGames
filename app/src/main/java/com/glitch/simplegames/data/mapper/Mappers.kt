package com.glitch.simplegames.data.mapper

import com.glitch.simplegames.data.model.response.Game
import com.glitch.simplegames.data.model.response.GameEntity
import com.glitch.simplegames.data.model.response.GameUI

fun List<GameEntity>.mapGameEntityToGameUI() = map {
	GameUI(
		gameId = it.gameId ?: 1,
		username = it.username.orEmpty(),
		title = it.title.orEmpty(),
		highScore = it.highScore ?: 0
	)
}


