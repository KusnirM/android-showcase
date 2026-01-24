# Hilt Workers
-keepnames @androidx.hilt.work.HiltWorker class * extends androidx.work.ListenableWorker

# Hilt ViewModels - keep names used as multibinding map key
# (Required after AGP 8.9.2 + Hilt 2.56.2)
-keepnames @dagger.hilt.android.lifecycle.HiltViewModel class * extends androidx.lifecycle.ViewModel

# Keep Hilt entry points
-keep,allowobfuscation,allowshrinking @dagger.hilt.EntryPoint class *
-keep,allowobfuscation,allowshrinking @dagger.hilt.android.EarlyEntryPoint class *
