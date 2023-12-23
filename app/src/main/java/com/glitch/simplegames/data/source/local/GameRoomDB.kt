package com.glitch.simplegames.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.glitch.simplegames.data.model.response.GameEntity

@Database(entities = [GameEntity::class], version = 1, exportSchema = false)
abstract class GameRoomDB : RoomDatabase() {
	abstract fun gameDao(): GameDao

	companion object {
		@Volatile
		private var INSTANCE: GameRoomDB? = null

		fun getInstance(context: Context): GameRoomDB {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext, GameRoomDB::class.java, "games_database"
				).build()
				INSTANCE = instance
				instance
			}
		}
	}
}