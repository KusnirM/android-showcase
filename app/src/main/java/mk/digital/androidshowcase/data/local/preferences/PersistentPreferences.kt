package mk.digital.androidshowcase.data.local.preferences

interface PersistentPreferences {
    suspend fun getPersistentCounter(): Int
    suspend fun setPersistentCounter(value: Int)

    suspend fun getThemeMode(): String
    suspend fun setThemeMode(mode: String)

    suspend fun getFcmToken(): String?
    suspend fun setFcmToken(value: String)
}

class PersistentPreferencesImpl(private val preferences: Preferences) : PersistentPreferences {

    override suspend fun getPersistentCounter(): Int = preferences.getInt(PERSISTENT_COUNTER_KEY) ?: 0
    override suspend fun setPersistentCounter(value: Int) = preferences.putInt(PERSISTENT_COUNTER_KEY, value)

    override suspend fun getThemeMode(): String = preferences.getString(THEME_MODE_KEY) ?: DEFAULT_THEME_MODE
    override suspend fun setThemeMode(mode: String) = preferences.putString(THEME_MODE_KEY, mode)

    override suspend fun getFcmToken(): String? = preferences.getString(FB_TOKEN_KEY)
    override suspend fun setFcmToken(value: String) = preferences.putString(FB_TOKEN_KEY, value)

    private companion object {
        private const val PERSISTENT_COUNTER_KEY = "persistent_counter"
        private const val THEME_MODE_KEY = "theme_mode"
        private const val DEFAULT_THEME_MODE = "SYSTEM"
        private const val FB_TOKEN_KEY = "fcm_token"
    }
}
