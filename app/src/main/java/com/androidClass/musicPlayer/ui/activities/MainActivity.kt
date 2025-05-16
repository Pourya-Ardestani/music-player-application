package com.androidClass.musicPlayer.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.androidClass.musicPlayer.AppDatabase
import com.androidClass.musicPlayer.ui.composable.HomeScreenParent
import com.androidClass.musicPlayer.ui.theme.AppTheme
import com.androidClass.musicPlayer.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * [MainActivity] is the main entry point of the app.
 * It is annotated with [@AndroidEntryPoint] to enable field injection via Hilt.
 * This class extends [ComponentActivity], which is a lean version of [AppCompatActivity].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * The [HomeViewModel] instance is obtained via the [viewModels] delegate,
     * which uses the activity as the ViewModelStoreOwner.
     */
    private val viewModel: HomeViewModel by viewModels()

    /**
     * The [onCreate] method is called when the activity is starting.
     * It sets up the UI content and associates it with the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down
     * then this Bundle contains the data it most recently supplied in [onSaveInstanceState].
     * Otherwise it is null.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (!isGranted) {
                    Toast.makeText(this@MainActivity, "audio Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_AUDIO
            ) == PackageManager.PERMISSION_GRANTED -> {

            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.READ_MEDIA_AUDIO

            ) -> {
                Toast.makeText(this@MainActivity, "audio Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }

            else -> {
                requestPermissionLauncher.launch(
                    Manifest.permission.READ_MEDIA_AUDIO
                )
            }
        }


        setContent {
            // Set the theme of the app to MusicPlayerJetpackComposeTheme.
            AppTheme {
                // Create a surface container using the Surface Composable.
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Setup the HomeScreenParent with the viewModel.
                    HomeScreenParent(viewModel)
                }
            }
        }
    }

}