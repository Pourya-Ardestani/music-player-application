package com.androidClass.musicPlayer.ui.activities

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.ui.composable.BottomSheetDialog
import com.androidClass.musicPlayer.ui.theme.AppTheme
import com.androidClass.musicPlayer.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.FileNotFoundException


@SuppressLint("RestrictedApi")
@AndroidEntryPoint
class PlayerActivity2 : androidx.activity.ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var track: Track.Builder = Track.Builder()
        if (Intent.ACTION_VIEW == intent.action) {
            val projection = arrayOf(
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.ArtistColumns.ARTIST,
                MediaStore.Audio.AudioColumns.ALBUM_ID
            )
            val c =
                intent.data?.let {
                    contentResolver.query(
                        it,
                        projection,
                        null,
                        null,
                        null
                    )
                }


            if (c != null) {
                c.moveToFirst()

                val path = c.getString(0)
                val artist = c.getString(2)
                val name = path.substring(path.lastIndexOf("/") + 1)
                val albumId: Long =
                    c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID))
                track =
                    Track.Builder().trackName(name).artistName(artist).trackUrl(path).trackId(1)
                try {
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        albumId
                    )
                    track.trackImage(
                        contentResolver.loadThumbnail(
                            contentUri,
                            Size(512, 512),
                            null
                        )
                    )
                } catch (e: FileNotFoundException) {
                    Log.e("TAG", "createTracks: ", e)
                }
                c.close()
            }
        } else {
            intent.getStringExtra("trackUrl")
                ?.let { track.trackUrl(it) }
            intent.getStringExtra("artistName")
                ?.let { track.artistName(it) }

            intent.getByteArrayExtra("trackImage")
                ?.let {
                    val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)
                    track.trackImage(bmp)
                }
            intent.getStringExtra("trackName")
                ?.let { track.trackName(it) }
        }


        setContent {
            // Set the theme of the app to MusicPlayerJetpackComposeTheme.
            AppTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                    BottomSheetDialog(
                        selectedTrack = track.build(),
                        playerEvents = viewModel,
                        viewModel.playbackState
                    )
                }
            }
        }
    }

}