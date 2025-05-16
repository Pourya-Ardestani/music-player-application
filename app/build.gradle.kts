import java.util.*

/**
 * Local properties loaded from the root project file.
 */
val localProperties = Properties().apply {
    load(rootProject.file(Constants.LOCAL_PROPERTIES).inputStream())
}

/**
 * Base URL for the API used by the app, loaded from local properties.
 */
val baseUrl: String = localProperties.getProperty(Constants.BASE_URL) ?: Constants.EMPTY_STRING

// Define the plugins for the application
plugins {
    id(Plugins.pluginAndroidApplication)
    id(Plugins.pluginKotlinAndroid)
    id(Plugins.pluginDevtoolsKSP)
    kotlin(Plugins.pluginKapt)
    id(Plugins.pluginHiltAndroid)
}

// Define the Android configurations for the application.
android {
    /**
     * The namespace for the resources in the project.
     */
    namespace = AppConfigs.namespace
    /**
     * The Android SDK version that the project should be compiled against.
     */
    compileSdk = AppConfigs.compileSdk

    /**
     * Enable or disable specific features for the build.
     */
    buildFeatures {
        buildConfig = true
    }

    /**
     * Configurations that apply to all build variants.
     */
    defaultConfig {
        applicationId = AppConfigs.applicationId
        minSdk = AppConfigs.minSdk
        targetSdk = AppConfigs.targetSdk
        versionCode = AppConfigs.versionCode
        versionName = AppConfigs.versionName

        testInstrumentationRunner = AppConfigs.testInstrumentationRunner

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(Constants.BASE_URL_TYPE, Constants.BASE_URL, "\"$baseUrl\"")
    }

    /**
     * Configuration for the different types of builds (e.g., debug, release).
     */
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(Constants.PROGUARD_ANDROID_OPTIMIZE),
                Constants.PROGUARD_RULES
            )
        }
    }
    /**
     * Configurations for the Java compiler.
     */
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    /**
     * Configurations for the Kotlin compiler.
     */
    kotlinOptions {
        jvmTarget = AppConfigs.jvmTarget
    }
    /**
     * Enable or disable Jetpack Compose.
     */
    buildFeatures {
        compose = true
    }
    /**
     * Configurations for Jetpack Compose.
     */
    composeOptions {
        kotlinCompilerExtensionVersion = AppConfigs.kotlinCompilerExtensionVersion
    }
    /**
     * Configurations for Jetpack Compose.
     */
    packaging {
        resources {
            excludes += Constants.EXCLUDES
        }
    }
}

// Configure Kotlin Annotation Processing (Kapt).
kapt { correctErrorTypes = true }

// Define the dependencies for the application.
dependencies {
    // Core Libraries
    implementation(Deps.coreKtx)

    // Jetpack Compose and related libraries
    implementation(platform(Deps.composeBom))
    implementation("androidx.room:room-common:2.6.1")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.7")
    implementation("androidx.test:runner:1.5.2")
    androidTestImplementation(Deps.composeBom)
    implementation(Deps.composeUI)
    implementation(Deps.composeUiGraphics)
    implementation(Deps.composeUiToolingPreview)
    implementation(Deps.composeMaterial3)
    implementation(Deps.composeMaterial)
    implementation(Deps.activityCompose)
    implementation(Deps.constraintLayoutCompose)
    implementation(Deps.lifecycRuntimeKtx)
    implementation(Deps.lifecycleViewModelCompose)
    implementation(Deps.lifecycleViewModelKtx)
    implementation(Deps.lifecycleRuntimeCompose)
    // Dagger Hilt for Dependency Injection
    implementation(Deps.hiltAndroid)
    kapt(Deps.hiltAndroidCompiler)

    // Media and Image Handling
    implementation(Deps.mediaExoPlayer)
    implementation(Deps.glideCompose)
    implementation(Deps.lottieCompose)

    // Testing Libraries
    testImplementation(Deps.testJUint)
    androidTestImplementation(Deps.androidTestJUnit)
    androidTestImplementation(Deps.androidTestExpresso)
    androidTestImplementation(Deps.testComposeUiTestJUnit4)

    implementation ("com.google.android.exoplayer:exoplayer:2.19.1") // Replace X.X with the latest version
    testImplementation(kotlin("test"))
    // Debugging Tools
    debugImplementation(Deps.debugComposeUiTooling)
   // debugImplementation(Deps.debugComposeUiTestManifest)

    // Add Truth library
    testImplementation ("com.google.truth:truth:1.1.3")

    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.7")
    androidTestImplementation ("androidx.compose.ui:ui-test-manifest:1.6.7")
    testImplementation ("org.mockito:mockito-core:4.2.0")
    testImplementation ("org.mockito:mockito-inline:4.2.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.7")
    androidTestImplementation ("org.mockito:mockito-core:4.2.0")
    androidTestImplementation ("org.mockito:mockito-inline:4.2.0")
    androidTestImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    ksp("androidx.room:room-compiler:$room_version")

    //debugImplementation ("androidx.compose.ui:ui-test-manifest:1.3.0")

    testImplementation ("io.mockk:mockk:1.12.3") // Replace with the latest version
    //androidTestImplementation ("androidx.test:testing-platform:1.0.1") // Replace with the latest version
    //androidTestImplementation("androidx.test:annotation:1.1.0-alpha04")
    //androidTestImplementation ("androidx.contrib:androidx-test-ext-junit:1.1.3")// Replace with the latest version
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")


    // To use the androidx.test.core APIs
    // androidTestImplementation("androidx.test:core:1.5.0")
    // Kotlin extensions for androidx.test.core
    // androidTestImplementation("androidx.test:core-ktx:1.5.0")

    // To use the androidx.test.espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // To use the JUnit Extension APIs
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // Kotlin extensions for androidx.test.ext.junit
    androidTestImplementation("androidx.test.ext:junit-ktx:1.1.5")

    // To use the Truth Extension APIs
    androidTestImplementation("androidx.test.ext:truth:1.5.0")

    // To use the androidx.test.runner APIs
    androidTestImplementation("androidx.test:runner:1.5.2")

    // To use android test orchestrator
    androidTestUtil("androidx.test:orchestrator:1.4.2")

    implementation("androidx.compose.ui:ui:1.6.7")



}