package com.androidClass.musicPlayer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.dbo.FavTrackDbo

@Database(entities = [Track::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): FavTrackDbo
}
