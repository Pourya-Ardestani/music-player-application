package com.androidClass.musicPlayer.di

import android.content.Context
import androidx.room.Room
import com.androidClass.musicPlayer.AppDatabase
import com.androidClass.musicPlayer.dbo.FavTrackDbo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideFavTrackDbo(appDatabase: AppDatabase): FavTrackDbo {
        return appDatabase.trackDao()
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "favTracks"
        ).allowMainThreadQueries().build()
    }
}