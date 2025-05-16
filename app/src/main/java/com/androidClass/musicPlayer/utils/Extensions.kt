package com.androidClass.musicPlayer.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.media3.common.MediaItem
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.player.MyPlayer
import com.androidClass.musicPlayer.player.PlaybackState
import com.androidClass.musicPlayer.player.PlayerStates
import com.androidClass.musicPlayer.player.PlayerStates.STATE_IDLE
import com.androidClass.musicPlayer.player.PlayerStates.STATE_PLAYING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


/**
 * Resets the state of each track in the list to the default state.
 */
fun MutableList<Track>.resetTracks() {
    this.forEach { track ->
        track.isSelected = false
        track.state = STATE_IDLE
    }
}

/**
 * Converts a list of [Track] objects into a mutable list of [MediaItem] objects.
 *
 * @return A mutable list of [MediaItem] objects.
 */
fun List<Track>.toMediaItemList(): MutableList<MediaItem> {
    return this.map { MediaItem.fromUri(it.trackUrl) }.toMutableList()
}

/**
 * Collects the player state from [myPlayer] and provides updates via the [updateState] function.
 *
 * @param myPlayer The player whose state is to be collected.
 * @param updateState A function to process the player state updates.
 */
fun CoroutineScope.collectPlayerState(
    myPlayer: MyPlayer, updateState: (PlayerStates) -> Unit
) {
    this.launch {
        myPlayer.playerState.collect {
            updateState(it)
        }
    }
}

/**
 * Launches a coroutine to periodically update the [playbackStateFlow] with the current
 * playback position and track duration from [myPlayer] as long as the player state is [STATE_PLAYING].
 *
 * @param playbackStateFlow The MutableStateFlow to be updated.
 * @param state The current player state.
 * @param myPlayer The player whose playback information is to be collected.
 */
fun CoroutineScope.launchPlaybackStateJob(
    playbackStateFlow: MutableStateFlow<PlaybackState>, state: PlayerStates, myPlayer: MyPlayer
) = launch {
    do {
        playbackStateFlow.emit(
            PlaybackState(
                currentPlaybackPosition = myPlayer.currentPlaybackPosition,
                currentTrackDuration = myPlayer.currentTrackDuration
            )
        )
        delay(1000) // delay for 1 second
    } while (state == STATE_PLAYING && isActive)
}

/**
 * Formats a long duration value (in milliseconds) into a time string in the format "MM:SS".
 *
 * @return The formatted time string.
 */
fun Long.formatTime(): String {
    val totalSeconds = this / 1000
    val minutes = totalSeconds / 60
    val remainingSeconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
fun drawableToBitmap(drawable: Drawable): Bitmap {
    var bitmap: Bitmap? = null
    if (drawable is BitmapDrawable) {
        val bitmapDrawable = drawable
        if (bitmapDrawable.bitmap != null) {
            return bitmapDrawable.bitmap
        }
    }
    bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        ) // Single color bitmap will be created of 1x1 pixel
    } else {
        Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    drawable.draw(canvas)
    return bitmap
}