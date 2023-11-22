package com.glitch.simplegames.data.source.local

import com.glitch.simplegames.data.model.response.Game

object Database {
    private val games = mutableListOf<Game>()

    fun getGames(): List<Game> {
        return games
    }

    fun addGames(id: Int, title: String, score: Int) {
        val newGame = Game(
            id = (games.lastOrNull()?.id ?: 0) + 1,
            title = title,
            score = score
        )
        games.add(newGame)
    }

}