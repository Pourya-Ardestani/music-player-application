package com.androidClass.musicPlayer.ui.composable

import org.junit.Assert.*
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.androidClass.musicPlayer.models.Track
import com.androidClass.musicPlayer.player.PlayerEvents
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class BottomPlayerTabTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomPlayerTabComponentsDisplayed() {
        // Arrange
        val selectedTrack = Track(
            artistName = "sample_artist_name",
            trackName = "Sample Track"
        )
        val playerEvents = mock<PlayerEvents>()

        // Act
        composeTestRule.setContent {
            BottomPlayerTab(selectedTrack, playerEvents, onBottomTabClick = {})
        }

        // Assert
        composeTestRule.onNodeWithTag("BottomPlayerTab").assertIsDisplayed()
        composeTestRule.onNodeWithTag("TrackImage").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PreviousIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("PlayPauseIcon").assertIsDisplayed()
        composeTestRule.onNodeWithTag("NextIcon").assertIsDisplayed()
    }
}



class BottomPlayerTabClickEventsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testBottomPlayerTabClickEvents() {
        // Arrange
        val selectedTrack = Track(
            artistName = "sample_artist_name",
            trackName = "Sample Track"
        )
        val playerEvents = mock(PlayerEvents::class.java)
        val onBottomTabClick = mock<() -> Unit>()

        // Act
        composeTestRule.setContent {
            BottomPlayerTab(selectedTrack, playerEvents, onBottomTabClick)
        }

        // Assert: Verify click on the entire bottom tab
        composeTestRule.onNodeWithTag("BottomPlayerTab").performClick()
        verify(onBottomTabClick).invoke()

        // Assert: Verify clicks on individual icons
        composeTestRule.onNodeWithTag("PreviousIcon").performClick()
        verify(playerEvents).onPreviousClick()

        composeTestRule.onNodeWithTag("PlayPauseIcon").performClick()
        verify(playerEvents).onPlayPauseClick()

        composeTestRule.onNodeWithTag("NextIcon").performClick()
        verify(playerEvents).onNextClick()
    }
}
