package com.glitch.simplegames.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_scores")
data class ScoreEntity(

    @PrimaryKey
    @ColumnInfo(name = "gameId")
    val gameId: Int?,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "score")
    val score: Int?
)
