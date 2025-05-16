package com.androidClass.musicPlayer.repositories

import com.androidClass.musicPlayer.models.Track

/**
 * An interface defining the operations for managing tracks.
 */
interface TrackRepository {

    /**
     * Retrieves a list of all tracks.
     *
     * @return a list of [Track] objects.
     */
    fun getTrackList(): List<Track>

    fun getFavTracks(): List<Track>
     fun getFavId(trackName:String):Track?
     fun addFav(track: Track)
     fun removeFav(track: Track)

}
