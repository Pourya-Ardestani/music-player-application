package com.androidClass.musicPlayer.di

import com.androidClass.musicPlayer.repositories.TrackRepository
import com.androidClass.musicPlayer.repositories.TrackRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module that provides an instance of [TrackRepository].
 * This module is installed in the [SingletonComponent], meaning that the provided [TrackRepository]
 * instance will be a singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    /**
     * Provides a singleton instance of [TrackRepository].
     *
     * @param trackRepository An instance of [TrackRepositoryImpl] which is a concrete implementation of [TrackRepository].
     * @return An instance of [TrackRepository].
     */
    @Provides
    @Singleton
    fun provideTrackRepository(trackRepository: TrackRepositoryImpl): TrackRepository {
        return trackRepository
    }
}