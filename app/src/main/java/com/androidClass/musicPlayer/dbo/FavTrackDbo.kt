package com.androidClass.musicPlayer.dbo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.androidClass.musicPlayer.models.Track

@Dao
interface FavTrackDbo {

    @Query("SELECT * FROM track WHERE trackName = :trackId LIMIT 1")
    fun findByTrackId(trackId: String): Track?


    @Query("SELECT * FROM track")
    fun getAll(): List<Track>
    @Insert
    fun insert(user: Track)

    @Delete
    fun delete(user: Track)
}
