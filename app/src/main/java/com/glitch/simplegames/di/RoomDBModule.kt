package com.glitch.simplegames.di

import android.content.Context
import androidx.room.Room
import com.glitch.simplegames.data.source.local.GameRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

	@Singleton
	@Provides
	fun provideScoreRoomDB(@ApplicationContext context: Context) =
		Room.databaseBuilder(context, GameRoomDB::class.java, "games_database").build()

	@Singleton
	@Provides
	fun provideScoreDao(roomDB: GameRoomDB) = roomDB.gameDao()
}