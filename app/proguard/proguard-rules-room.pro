# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao class *

# Keep Room generated implementation
-keep class * implements androidx.room.RoomDatabase$Callback { *; }

# Room with Kotlin - keep suspend functions
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public abstract ** *Dao();
}
