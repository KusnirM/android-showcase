# ----------------------------
# DTOs from KMP shared
# ----------------------------
-keep class mk.digital.androidshowcase.data.dto.** { *; }

# ----------------------------
# Android
# ----------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-dontwarn com.google.crypto.tink.subtle.**

# ----------------------------
# AndroidX Activity
# ----------------------------
-dontwarn androidx.activity.**
-keep class androidx.activity.** { *; }

# ----------------------------
# Jetpack Compose
# ----------------------------
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ----------------------------
# CameraX
# ----------------------------
-keep class androidx.camera.core.** { *; }
-dontwarn androidx.camera.**

# ----------------------------
# Logs
# ----------------------------
-keep class mk.digital.androidshowcase.util.Logger { *; }
-keepattributes SourceFile,LineNumberTable

-assumenosideeffects class android.util.Log {
  public static int v(...);
  public static int d(...);
  public static int i(...);
  public static int wtf(...);
}

# ----------------------------
# JUnit (only for test builds)
# ----------------------------
-dontwarn org.junit.**
-keep class org.junit.** { *; }

# ----------------------------
# Generic fallback
# ----------------------------
-dontwarn kotlin.**
-keep class kotlin.** { *; }
-keepclassmembers class kotlin.Metadata { *; }

-keep class * {
    @kotlin.Metadata *;
}

# ----------------------------
# SLF4J
# ----------------------------
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# ----------------------------
# Kotlinx Serialization
# ----------------------------
-keep class kotlinx.serialization.Serializable
-keepclassmembers class kotlinx.serialization.** { *; }
-keepclasseswithmembers class kotlinx.serialization.** { *; }

# ----------------------------
# Kotlin Coroutines
# ----------------------------
-keepclassmembers class kotlinx.coroutines.** { *; }