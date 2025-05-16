package com.androidClass.musicPlayer.repositories

import android.content.ContentResolver
import android.os.Build
import androidx.annotation.RequiresApi
import org.junit.Assert.*
import org.junit.Test
import com.androidClass.musicPlayer.models.Track
import org.junit.Assert.*  // Assuming JUnit for assertions (assertTrue, assertEquals, etc.)
import org.junit.BeforeClass
import kotlin.test.assertNotNull  // If using additional assertions from Kotlin test library
import org.mockito.Mock  // If using Mockito for mocking (replace with your preferred mocking library)
import org.mockito.Mockito.`when`  // If using Mockito for mocking (replace with your preferred mocking library syntax)
import org.junit.Assert.assertTrue  // Assuming you only use these specific assertions
import org.junit.Assert.assertEquals
import org.junit.contrib.android.modernizer.Modernizer // Assuming using androidx libraries
import androidx.contrib.android.modernizer


import android.database.Cursor
import io.mockk.every
import io.mockk.mockk
import android.content.Context
import androidx.test.filters.SdkSuppress
import java.io.FileNotFoundException


@Modernizer // Add this if your project uses androidx libraries
@SdkSuppress(minSdkVersion = Build.VERSION_CODES.Q)  // Assuming the original class requires API level 29
class TrackRepositoryImplTest {

    fun testCreateTracks() {

        // Create a mock Context object to avoid real interactions with the device
        val mockContext = mockk<Context>()

        // Mock cursor behavior (optional, depending on your test)
        every { mockContext.contentResolver.query(any(), any(), any(), any(), any()) } returns null

        // Create a mock of TrackRepositoryImpl
        val mockRepository = mockk<TrackRepositoryImpl>()

        // Assuming some logic sets the real repository on the mock (replace with your logic)
        every { mockRepository.createTracks(mockContext) } returns Unit  // Mock the behavior

        // Use the mock repository in your test
        mockRepository.createTracks(mockContext)

        // Verify that the tracks list is not empty after fetching tracks
        assertTrue(mockRepository.getTrackList().isNotEmpty()) // Use the mock's methods

        // Additional Assertions (Optional)
        // You can add more assertions here to verify specific details about the tracks
        // For example, if there are known songs on the device, you can check their presence
        // in the list or verify some properties of the first track.
    }

    @Test
    @SdkSuppress(minSdkVersion = Build.VERSION_CODES.Q)  // Suppress min SDK requirement for this test
    fun testEmptyTrackList() {
        val mockContext = mockk<Context>()

        // Configure mock Context to return null cursor (no media files)
        every { mockContext.contentResolver.query(any(), any(), any(), any(), any()) } returns null

        val repository = TrackRepositoryImpl(mockContext)
        repository.createTracks(mockContext)

        assertTrue(repository.getTrackList().isEmpty())
    }

    @Test
    fun testTrackName() {
        val mockContext = mockk<Context>()

        // Mock cursor data with specific file path
        val mockCursor = mockk<Cursor>()
        every { mockCursor.getString(0) } returns "/storage/music/sample_song.mp3"
        //every { mockContext.contentResolver.query(any(), any(), any(), any(), any()) } returns mockCursor.cursor
        every { mockContext.contentResolver.query(any(), any(), "name", any(), any()) } returns null // bejaye qabli

        val repository = TrackRepositoryImpl(mockContext)
        repository.createTracks(mockContext)

        val trackList = repository.getTrackList()
        val firstTrack = trackList.firstOrNull()

        assertNotNull(firstTrack)
        assertEquals("sample_song", firstTrack?.trackName)
    }

    @Test
    fun testTrackImageLoadingFailure() {
        val mockContext = mockk<Context>()

        // Mock cursor data
        val mockCursor = mockk<Cursor>()
        every { mockCursor.getString(any()) } returns any()
      //  every { mockContext.contentResolver.query(any(), any(), any(), any(), any()) } returns mockCursor.cursor
        every { mockContext.contentResolver.query(any(), any(), "Image", any(), any()) } returns null // bejaye qabli


        // Mock FileNotFoundException for track image loading
        val mockContentResolver = mockk<ContentResolver>()
        every { mockContext.contentResolver } returns mockContentResolver
        every { mockContentResolver.loadThumbnail(any(), any(), any()) } throws FileNotFoundException("Failed to load album art")

        val repository = TrackRepositoryImpl(mockContext)
        repository.createTracks(mockContext)

        val trackList = repository.getTrackList()

        // Verify track is added with null image
        assertTrue(trackList.isNotEmpty())
        val firstTrack = trackList.firstOrNull()
        assertNull(firstTrack?.trackImage)
    }

}
