package mk.digital.androidshowcase.domain.repository

fun interface ClearableCache {
    suspend fun clear()
}
