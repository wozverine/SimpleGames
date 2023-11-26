package com.glitch.simplegames.di

import com.glitch.simplegames.data.repository.ScoreRepository
import com.glitch.simplegames.data.source.local.ScoreDao
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
		scoreDao: ScoreDao
	) = ScoreRepository(scoreDao)
}
