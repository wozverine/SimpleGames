package com.glitch.simplegames.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.glitch.simplegames.data.model.response.ScoreEntity

@Database(entities = [ScoreEntity::class], version = 1, exportSchema = false)
abstract class ScoreRoomDB : RoomDatabase() {

    abstract fun scoreDao(): ScoreDao
}