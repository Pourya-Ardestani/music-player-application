package com.androidClass.musicPlayer.ui.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.androidClass.musicPlayer.R
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.player.PlayerStates.STATE_PLAYING

/**
 * A composable function that displays a list item for a track.
 * The list item includes the track's image, name, and artist.
 * Also includes a click action for the track.
 *
 * @param track The track to be displayed.
 * @param onTrackClick The action to be performed when the track item is clicked.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TrackListItem(track: Track, onTrackClick: () -> Unit, onTrackLongClick: () -> Unit) {
    val bgColor =
        if (track.isSelected) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.surfaceVariant
    val textColor =
        if (track.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.scrim
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(all = 5.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = bgColor)
            .combinedClickable(
                onClick = onTrackClick,
                onLongClick = onTrackLongClick,
            )
    ) {
        TrackImage(trackImage = track.trackImage, modifier = Modifier.size(size = 64.dp))
        Column(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .weight(weight = 1f)
        ) {

            Text(
                text = track.trackName,
                color = textColor,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .basicMarquee()
            )
            Text(
                text = track.artistName, color = textColor, maxLines = 1,
                modifier = Modifier
                    .basicMarquee()
            )
        }
        if (track.state == STATE_PLAYING) LottieAudioWave()
    }
}

/**
 * A composable function that displays a Lottie animation of an audio wave.
 * The animation loops indefinitely.
 */
@Composable
fun LottieAudioWave() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.audio_wave))
    LottieAnimation(
        composition = composition,
        iterations = Int.MAX_VALUE,
        modifier = Modifier.size(size = 64.dp)
    )
}