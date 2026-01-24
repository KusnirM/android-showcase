package mk.digital.androidshowcase.data.local.preferences

interface SessionPreferences {

    suspend fun setSessionCounter(value: Int)
    suspend fun getSessionCounter(): Int
    suspend fun clear()
}

class SessionPreferencesImpl(private val preferences: Preferences) : SessionPreferences {

    override suspend fun setSessionCounter(value: Int) {
        preferences.putInt(SESSION_COUNTER_KEY, value)
    }

    override suspend fun getSessionCounter(): Int = preferences.getInt(SESSION_COUNTER_KEY) ?: 0

    override suspend fun clear() {
        preferences.clear()
    }

    private companion object {
        private const val SESSION_COUNTER_KEY = "session_counter"
    }
}
