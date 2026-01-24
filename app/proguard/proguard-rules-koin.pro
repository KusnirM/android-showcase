# Koin core
-keep class org.koin.** { *; }
-keepclassmembers class * {
    @org.koin.core.annotation.* <methods>;
}
-dontwarn org.koin.**

# Koin needs to access constructors via reflection
-keepclassmembers class * {
    public <init>(...);
}