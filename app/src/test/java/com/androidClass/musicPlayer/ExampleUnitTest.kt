package com.androidClass.musicPlayer

import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule

//import com.google.android.exoplayer2.*
//import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}
/**
@RunWith(AndroidJUnit4::class)
class MyPlayerTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var myPlayer: MyPlayer

    @Before
    fun setUp() {
        exoPlayer = mockk()
        myPlayer = MyPlayer(exoPlayer)
    }

    @Test
    fun testInitialization() {
        val mockTrackList = mutableListOf<MediaItem>()
        every { exoPlayer.addListener(any()) } just runs
        every { exoPlayer.setMediaItems(mockTrackList) } just runs
        every { exoPlayer.prepare() } just runs

        myPlayer.iniPlayer(mockTrackList)

        verify {
            exoPlayer.addListener(any())
            exoPlayer.setMediaItems(mockTrackList)
            exoPlayer.prepare()
        }
    }

    @Test
    fun testPlaybackStateEmission() = runBlocking {
        val mockPlaybackState = Player.STATE_READY
        val mockPlayWhenReady = true
        val mockReason = Player.PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST

        myPlayer.onPlaybackStateChanged(mockPlaybackState)
        myPlayer.onPlayWhenReadyChanged(mockPlayWhenReady, mockReason)

        val playerState = myPlayer.playerState.first()

        assertThat(playerState).isAnyOf(MyPlayer.STATE_READY, MyPlayer.STATE_PLAYING)
    }
}*/
