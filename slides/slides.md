---
theme: penguin
class: text-center
highlighter: prism
lineNumbers: false
info: "Musicolet"
drawings:
  persist: false
transition: slide-left
title: "Musicolet"
mdc: true
layout: intro
fonts:
  sans: "Poppins"
  serif: "MuseoModerno"
  mono: "Fira Code"
  local: "MuseoModerno,Poppins"
---

# Musicolet

Personation the Musicolet app
_Group 14_

---
---

<Toc />

---
---

# What was the problem

Building a new simple, ad-free music player can be beneficial for several reasons:

1. **User Experience**: Ads can be intrusive and disrupt the listening experience. A simple, ad-free music player ensures that users can enjoy their music without interruptions¹.

2. **Privacy**: Some music players with ads may require internet permissions to fetch ads, which could potentially compromise user privacy. An ad-free player that doesn't require internet access can offer a more private experience¹.

3. **Performance**: Without the need to load ads, a simple music player can be more lightweight and have faster performance, especially on devices with limited resources¹.
   In summary, a new simple, ad-free music player can offer a more enjoyable, private, and efficient way to listen to music, with a focus on the features that matter most to users.

---
layout: new-def
---
# what is our app does?

If you’re looking for an app that embodies the features of a simple, ad-free music player without the need for a detailed list, you might want to explore Musicolet Music Player. It’s designed to provide a seamless music listening experience with a focus on privacy, performance, and user control. The app is well-regarded for its offline functionality, customizable user interface, and robust feature set, all while being completely free of ads.

---
---

# Main features

Here's a breakdown of its features:

- **Ad-Free**: Musicolet is completely free of ads, ensuring an uninterrupted music experience.
- **Offline Functionality**: It does not require internet permissions, as it operates entirely offline. This means you can play music that you've downloaded and saved on your device without worrying about privacy concerns.
- **User Interface**: The app boasts a minimalist UI design, making it easy to navigate and manage your music library.
- **Easy access to favorite songs**: This feature typically includes options to mark songs as favorites and create a dedicated playlist or tab for easy retrieval, ensuring your top tunes are always at your fingertips.

---
---

# 💻 Tech Stacks

Here's a summarized overview of the Android development tools and libraries you mentioned:

- [Material3]() A library for implementing Material Design components in Jetpack Compose UIs, providing ready-to-use design elements.
- [ModalBottomSheetLayout]() The Compose equivalent of BottomSheetDialog, allowing for the implementation of modal bottom sheets in a Compose UI.
- [Media3 ExoPlayer]() An app-level media player for video and audio playback, supporting both local files and internet streaming, with basic controls like play, pause, and seek.
- [Compose Lottie]() A library for rendering animations created in Adobe After Effects, exported as JSON, and displayed natively on Android and iOS.
- [Dagger Hilt]() A dependency injection framework for Android that simplifies the process by reducing the need for manual boilerplate code.
- [Room](): An abstraction layer over SQLite for robust database management in Android apps, simplifying data persistence.

---
---

# Metrics

- **User Engagement**: Measuring how frequently and for how long users interact with the app.

- **User Retention**: The rate at which users continue to use the app over time.

- **App Performance**: Tracking the app's stability, speed, and reliability.

- **Customer Satisfaction**: Gathering user feedback through ratings, reviews, and surveys to gauge overall satisfaction with the app.

These metrics help in understanding the app's performance from different angles, ensuring continuous improvement and user satisfaction.

---
---

# Road map

- **Tag Editor**: You can edit tags and album arts of multiple songs at once, allowing for better organization of your music files.
- **Powerful Equalizer**: It includes a powerful equalizer with Bass Boost and Surround Sound options, along with other presets to enhance your listening experience.
- **Multiple Queues**: Musicolet allows you to create separate queues for each folder, album, artist, or playlist, and you can resume them from their last position anytime.
- **Customization**: The app offers customization options such as creating synchronized lyrics, saving bookmarks and notes, and adding shortcuts to your home screen.
- **Widgets and Lock Screen Controls**: It provides widgets and lock screen controls with queue and lyrics display, enhancing accessibility and control.
- **Backup and Restore**: You can back up and restore your settings and playlists, which is handy if you reset or change your phone.

---
layout: center
---
<div style="height: 10vh" class="flex flex-col justify-center items-center">
<span class="text-7xl font-bold bg-gradient-to-r from-orange-700 via-blue-500 to-green-400 text-transparent bg-clip-text bg-300% animate-gradient p-0 m-0 h-42">
 Check it live
</span>
</div>

---
layout: new-defsm
---
# How we build the app ?

---
layout: two-cols-gl
---
# App Module

```c
│   AppDatabase.kt
│   MyApplication.kt
├───dbo
│       FavTrackDbo.kt
├───di
│       AppModule.kt
│       DatabaseModule.kt
│       DataModule.kt
├───models
│       Track.kt
├───player
│       MyPlayer.kt
│       PlayBackState.kt
│       PlayerEvents.kt
│       PlayerStates.kt
├───repositories
│       TrackRepository.kt
│       TrackRepositoryImpl.kt

```

::right::

```c
├───ui
│   ├───activities
│   │       MainActivity.kt
│   │       PlayerActivity2.kt
│   ├───composable
│   │       BottomPlayerTab.kt
│   │       BottomSheetDialog.kt
│   │       HomeScreenParent.kt
│   │       SharedComposables.kt
│   │       TrackListItem.kt
│   └───theme
│           Color.kt
│           Theme.kt
│           Type.kt
├───utils
│       Extensions.kt
└───viewmodels
        HomeVIewModel.kt
```

---
layout: two-cols-gl
---
# How to find musics in device ?

```kotlin{all|3-8|all}
 val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
val projection = arrayOf(
  MediaStore.Audio.AudioColumns.DATA,
  MediaStore.Audio.AudioColumns.ALBUM,
  MediaStore.Audio.ArtistColumns.ARTIST,
  MediaStore.Audio.AudioColumns.ALBUM_ID,
  MediaStore.Audio.AudioColumns.TITLE )
 val c = context.contentResolver.query(
   uri,
   projection,
   null,
   null,
  null
  )
```
::right::

```kotlin{all|3-8}
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
)
```

---
layout: two-cols-gl
---
# How we save the favorites
```kotlin
@Dao
interface FavTrackDbo {
  @Query("SELECT * FROM track WHERE 
          trackName = :trackId LIMIT 1")
  fun findByTrackId(trackId: String): Track?
  @Query("SELECT * FROM track")
   fun getAll(): List<Track>
   @Insert
   fun insert(user: Track)
   @Delete
   fun delete(user: Track)
}
```
::right::

```kotlin
override fun addOrRemoveFromFav(track: Track): Boolean {
 return if (trackRepository.getFavId(track.trackName) != null) {
    trackRepository.removeFav(track)
   false
 } else {
   trackRepository.addFav(track)
    true
  }
  }
```
---
layout: two-cols-gl
---
# how to handel others app request for play ?
```xml{2-3|5-7|all}
<intent-filter >
 <action android:name="android.intent.action.VIEW" />
 <category android:name="android.intent.category.DEFAULT" />

 <data android:scheme="content"/>
  <data android:scheme="file"/>
  <data android:mimeType="audio/*"/>
  </intent-filter>
```
::right::
# Needed Permissions
```xml{all|1|3-5|7|8-14}
<uses-permission android:name="android.permission.INTERNET" />

<permission
 android:name="android.permission.MEDIA_CONTENT_CONTROL"
 tools:ignore="ReservedSystemPermission" />

<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
<uses-permission
  android:name="android.permission.READ_EXTERNAL_STORAGE"
  android:maxSdkVersion="32" />
 <uses-permission
 android:name="android.permission.WRITE_EXTERNAL_STORAGE"
android:maxSdkVersion="29"
 tools:ignore="ScopedStorage" />
```
---
---
# How we handel the shortcuts
```xml{all|3-9|10-13}
<shortcuts xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <shortcut
        android:shortcutId="fav"
        android:enabled="true"
        android:icon="@mipmap/ic_short"
        android:shortcutShortLabel="@string/shortcutShortLabel"
        android:shortcutLongLabel="@string/shortcutShortLabel"
        tools:ignore="UnusedAttribute">
        <intent
            android:action="android.intent.action.VIEW"
            android:targetPackage="com.androidClass.musicPlayer"
            android:targetClass="com.androidClass.musicPlayer.ui.activities.MainActivity" />
    </shortcut>
</shortcuts>

```

---
layout: center
---
<div style="height: 10vh" class="flex flex-col justify-center items-center">
<span class="text-7xl font-bold bg-gradient-to-r from-orange-700 via-blue-500 to-green-400 text-transparent bg-clip-text bg-300% animate-gradient p-0 m-0 h-42">
 Thank You
</span>

<p style="height: 10vh" class="text-2xl font-bold bg-gradient-to-r from-orange-700 via-blue-500 to-green-400 text-transparent bg-clip-text bg-300% animate-gradient p-0 m-0">
 Hope you have good day
</p>
</div>

<style>
.animate-gradient {
  background-size: 300%;
  -webkit-animation: animatedgradient 6s ease infinite alternate;
  -moz-animation: animatedgradient 6s ease infinite alternate;
  animation: animatedgradient 6s ease infinite alternate;
}

@keyframes animatedgradient {
  0% {
    background-position: 0% 50%;
  }
  50% {
    background-position: 100% 50%;
  }
  100% {
    background-position: 0% 50%;
  }
}
</style>
