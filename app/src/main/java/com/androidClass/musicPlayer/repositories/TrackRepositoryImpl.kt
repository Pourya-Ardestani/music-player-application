package com.androidClass.musicPlayer.repositories

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.core.database.getStringOrNull
import androidx.core.graphics.drawable.toDrawable
import com.androidClass.musicPlayer.R
import com.androidClass.musicPlayer.dbo.FavTrackDbo
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.utils.drawableToBitmap
import java.io.FileNotFoundException
import javax.inject.Inject


/**
 * A concrete implementation of the [TrackRepository] interface.
 * This class is responsible for managing and providing tracks.
 *
 * @constructor Creates an instance of [TrackRepositoryImpl].
 */
@RequiresApi(Build.VERSION_CODES.Q)
class TrackRepositoryImpl @Inject constructor(context: Context, private val trackDbo: FavTrackDbo) :
    TrackRepository {

    /**
     * A list of tracks stored in-memory.
     */
    private val tracks = mutableListOf<Track>()

    /**
     * Initializes the tracks repository.
     */
    init {
        // Initialize songs here or load from a data source
        createTracks(context)
    }

    /**
     * Retrieves a list of all tracks in the repository.
     *
     * @return a list of [Track] objects.
     */
    override fun getTrackList(): List<Track> {
        return tracks
    }

    override fun getFavTracks(): List<Track> {
        return trackDbo.getAll()
    }

    override fun getFavId(trackName: String): Track? {
        return trackDbo.findByTrackId(trackName)
    }

    override fun removeFav(track: Track) {
        trackDbo.delete(track)
    }

    override fun addFav(track: Track) {
        trackDbo.insert(track)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("Range", "Recycle", "SuspiciousIndentation")
    private fun createTracks(context: Context) {
        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,
            MediaStore.Audio.AudioColumns.ALBUM_ID,
            MediaStore.Audio.AudioColumns.TITLE
        )
        val c = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        var i = 0;
        if (c != null) {
            while (c.moveToNext()) {
                i++;
                val path = c.getString(0)
                val album = c.getString(1)
                val artist = c.getString(2)
                val name = if (c.getStringOrNull(4) == null) {
                    path.substring(path.lastIndexOf("/") + 1)
                } else c.getString(4)
                val albumId: Long =
                    c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))


                val track =
                    Track.Builder().trackName(name).artistName(artist).album(album).trackUrl(path)
                        .trackId(i)
                try {
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        albumId
                    )
                    track.trackImage(
                        context.contentResolver.loadThumbnail(
                            contentUri,
                            Size(512, 512),
                            null
                        )
                    )
                } catch (e: FileNotFoundException) {
                    Log.e("TAG", "createTracks: ", e)
                    track.trackImage(drawableToBitmap(R.drawable.no_pictures.toDrawable()))
                }

                tracks.add(track.build())
            }
            c.close()
        }
    }

}