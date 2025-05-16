package com.androidClass.musicPlayer.models

import android.graphics.Bitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.androidClass.musicPlayer.BuildConfig
import com.androidClass.musicPlayer.R
import com.androidClass.musicPlayer.player.PlayerStates
import com.androidClass.musicPlayer.player.PlayerStates.STATE_IDLE
import com.androidClass.musicPlayer.utils.drawableToBitmap

/**
 * Represents a single music track.
 *
 * @property trackId The unique identifier for the track.
 * @property trackName The name of the track.
 * @property trackUrl The URL of the track.
 * @property trackImage The resource identifier of the track's image.
 * @property artistName The name of the artist of the track.
 * @property isSelected Indicates if the track is currently selected.
 * @property state The current playback state of the track.
 */
@Entity
data class Track(
    @PrimaryKey var trackId: Int = 0,
    var trackName: String = "",
    var trackUrl: String = "",
    var album: String = "",
    @Ignore var trackImage: Bitmap = drawableToBitmap(R.drawable.no_pictures.toDrawable()),
    var artistName: String = "",
    @Ignore var isSelected: Boolean = false,
    @Ignore var state: PlayerStates = STATE_IDLE
) {
    /**
     * Builder class for [Track].
     *
     * This allows for the incremental construction of a [Track] object.
     */
    class Builder {
        private var trackId: Int = 0
        private lateinit var trackName: String
        private lateinit var album: String
        private lateinit var trackUrl: String
        private var trackImage: Bitmap = drawableToBitmap(R.drawable.no_pictures.toDrawable())
        private lateinit var artistName: String
        private var isSelected: Boolean = false
        private var state: PlayerStates = STATE_IDLE

        fun trackId(trackId: Int) = apply { this.trackId = trackId }
        fun trackName(trackName: String) = apply { this.trackName = trackName }
        fun trackUrl(trackUrl: String) = apply { this.trackUrl = BuildConfig.BASE_URL + trackUrl }
        fun trackImage(trackImage: Bitmap) = apply { this.trackImage = trackImage }
        fun artistName(artistName: String) = apply { this.artistName = artistName }
        fun album(album: String) = apply { this.album = album }

        /**
         * Builds and returns a [Track] object.
         *
         * @return A [Track] object with the set properties.
         */
        fun build(): Track {
            return Track(
                trackId,
                trackName,
                trackUrl,
                album,
                trackImage,
                artistName,
                isSelected,
                state,
            )
        }
    }


}
