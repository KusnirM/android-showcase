import com.google.firebase.appdistribution.gradle.firebaseAppDistribution
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.fb.crashlytics)
    alias(libs.plugins.firebase.distribution)
    alias(libs.plugins.paparazzi)
}


val keystorePropertiesFile = project.file("keystore.properties")
val keystoreProperties = Properties()

if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(keystorePropertiesFile.inputStream())
}

val localPropertiesFile = rootProject.file("local.properties")
val localProperties = Properties()

if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

val hasSigningProperties = keystoreProperties.isNotEmpty()

android {
    namespace = "mk.digital.androidshowcase"
    compileSdk = libs.versions.androidCompileSdk.get().toInt()

    defaultConfig {
        applicationId = "mk.digital.androidshowcase"
        minSdk = libs.versions.androidMinSdk.get().toInt()
        targetSdk = libs.versions.androidTargetSdk.get().toInt()
        versionCode = (project.findProperty("versionCode") as String?)?.toIntOrNull() ?: 1
        versionName = (project.findProperty("versionName") as String?) ?: "1.0.0"
    }

    signingConfigs {
        if (hasSigningProperties) {
            create("release") {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            }
        }
    }

    buildTypes {
        debug {
            isDefault = true
            isDebuggable = true
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            val extraProguardFiles = fileTree("$projectDir/proguard") {
                include("*.pro")
            }.files.toTypedArray()

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                *extraProguardFiles
            )

            if (hasSigningProperties) {
                signingConfig = signingConfigs.getByName("release")
            }

            firebaseAppDistribution {
                appId = "1:937159334013:android:7a54b0b475781fcd5cf914"
                artifactType = "APK"
                groups = localProperties["fb.test.group"]?.toString() ?: "testers"
            }
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        optIn.addAll(
            "androidx.compose.material3.ExperimentalMaterial3Api",
            "kotlin.uuid.ExperimentalUuidApi",
            "com.google.accompanist.permissions.ExperimentalPermissionsApi"
        )
        freeCompilerArgs.addAll(
            "-Xexpect-actual-classes"
        )
    }
}

//keep for tests: JUnit 5 + Vintage for JUnit4 (Paparazzi)
tasks.withType<Test> {
    useJUnitPlatform()

    // -PscreenshotOnly: run ONLY screenshot tests
    // no flag: run ONLY regular unit tests (exclude screenshot tests)
    val runScreenShotTests = project.hasProperty("screenshotOnly")
    val screenshotTestFile = "*ScreenshotTest*"
    filter {
        if (runScreenShotTests) includeTestsMatching(screenshotTestFile)
        else excludeTestsMatching(screenshotTestFile)

    }
}

dependencies {
    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.activity.compose)
    implementation(libs.android.material)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.compose.foundation)
    implementation(libs.compose.runtime)

    // Navigation 3
    implementation(libs.navigation3.runtime)
    implementation(libs.navigation3.ui)
    implementation(libs.lifecycle.viewmodel.navigation3)

    // Lifecycle & ViewModel
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Hilt DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit & OkHttp - Networking
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Room Database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // AndroidX Extras
    implementation(libs.exifinterface)
    implementation(libs.appcompat)

    // DataStore
    implementation(libs.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Serialization
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.datetime)

    // Images - Coil 3
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.svg)

    // Biometric
    implementation(libs.androidx.biometric)

    // Camera
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    // Barcode & Scanner
    implementation(libs.mlkit.barcode)
    implementation(libs.zxing.core)

    // Google Services
    implementation(libs.accompanist.permissions)
    implementation(libs.google.services.location)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.app.check)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.messaging)

    // Testing
    testImplementation(libs.junit4)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    testRuntimeOnly(libs.junit.vintage.engine)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.paparazzi)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}
