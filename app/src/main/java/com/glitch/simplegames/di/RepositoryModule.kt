package com.glitch.simplegames.di

import com.glitch.simplegames.data.repository.GameRepository
import com.glitch.simplegames.data.source.local.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
	@Provides
	@Singleton
	fun provideScoreRepository(
		gameDao: GameDao
	) = GameRepository(gameDao)
}
