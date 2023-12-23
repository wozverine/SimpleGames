package com.glitch.simplegames.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "games")
data class GameEntity(

	@PrimaryKey
	@ColumnInfo(name = "gameId")
	val gameId: Int?,

	@ColumnInfo(name = "title")
	val title: String?,

	@ColumnInfo(name = "username")
	val username: String?,

	@ColumnInfo(name = "highScore")
	val highScore: Int?
)
