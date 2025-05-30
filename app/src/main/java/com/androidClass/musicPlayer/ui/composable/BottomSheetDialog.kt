package com.androidClass.musicPlayer.ui.composable

import android.graphics.Bitmap
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.androidClass.musicPlayer.R
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.player.PlaybackState
import com.androidClass.musicPlayer.player.PlayerEvents
import com.androidClass.musicPlayer.utils.formatTime
import kotlinx.coroutines.flow.StateFlow
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

/**
 * [BottomSheetDialog] is a composable that represents the bottom sheet dialog which contains information about the selected track,
 * a slider to monitor and control track progress, and controls for track playback.
 *
 * @param selectedTrack The [Track] object that is currently selected for playback.
 * @param playerEvents The [PlayerEvents] object which encapsulates all the events associated with the player like play, pause, next, previous.
 * @param playbackState A [StateFlow] object representing the playback state, including current playback position and track duration.
 */
@Composable
fun BottomSheetDialog(
    selectedTrack: Track, playerEvents: PlayerEvents, playbackState: StateFlow<PlaybackState>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TrackInfo(
            trackImage = selectedTrack.trackImage,
            trackName = selectedTrack.trackName,
            artistName = selectedTrack.artistName,
            album = selectedTrack.album
        )
        TrackProgressSlider(playbackState = playbackState) {
            playerEvents.onSeekBarPositionChanged(it)
        }
        TrackControls(
            selectedTrack = selectedTrack,
            onPreviousClick = playerEvents::onPreviousClick,
            onPlayPauseClick = playerEvents::onPlayPauseClick,
            onNextClick = playerEvents::onNextClick
        )
    }
}

/**
 * [TrackInfo] is a composable that displays the image, name, and artist of a track.
 *
 * @param trackImage The resource ID of the track image.
 * @param trackName The name of the track.
 * @param artistName The name of the artist.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackInfo(trackImage: Bitmap, trackName: String, artistName: String, album: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 350.dp)
    ) {
        TrackImage(
            trackImage = trackImage, modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp)
        )
    }
    Text(
        text = trackName,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )

    Text(
        text = "$artistName.$album", modifier = Modifier.basicMarquee()
    )

}

/**
 * [TrackProgressSlider] is a composable that represents a slider for tracking and controlling the progress of the current track.
 *
 * @param playbackState A [StateFlow] object representing the playback state, including current playback position and track duration.
 * @param onSeekBarPositionChanged A lambda which gets executed when the position of the slider is changed.
 */
@Composable
fun TrackProgressSlider(
    playbackState: StateFlow<PlaybackState>, onSeekBarPositionChanged: (Long) -> Unit
) {
    val playbackStateValue = playbackState.collectAsState(
        initial = PlaybackState(0L, 0L)
    ).value
    var currentMediaProgress = playbackStateValue.currentPlaybackPosition.toFloat()
    var currentPosTemp by rememberSaveable { mutableStateOf(0f) }

    Slider(
        value = if (currentPosTemp == 0f) currentMediaProgress else currentPosTemp,
        onValueChange = { currentPosTemp = it },
        onValueChangeFinished = {
            currentMediaProgress = currentPosTemp
            currentPosTemp = 0f
            onSeekBarPositionChanged(currentMediaProgress.toLong())
        },
        valueRange = 0f..playbackStateValue.currentTrackDuration.toFloat(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = playbackStateValue.currentPlaybackPosition.formatTime(),
        )
        Text(
            text = playbackStateValue.currentTrackDuration.formatTime(),
        )
    }
}

/**
 * [TrackControls] is a composable that represents the controls for track playback, including previous, play/pause, and next buttons.
 *
 * @param selectedTrack The [Track] object that is currently selected for playback.
 * @param onPreviousClick A lambda which gets executed when the previous button is clicked.
 * @param onPlayPauseClick A lambda which gets executed when the play/pause button is clicked.
 * @param onNextClick A lambda which gets executed when the next button is clicked.
 */
@Composable
fun TrackControls(
    selectedTrack: Track,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PreviousIcon(onClick = onPreviousClick, isBottomTab = false)
        PlayPauseIcon(
            selectedTrack = selectedTrack, onClick = onPlayPauseClick, isBottomTab = false
        )
        NextIcon(onClick = onNextClick, isBottomTab = false)
    }
}