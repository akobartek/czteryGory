package pl.kapucyni.gory4.app.common.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull

class PreferencesRepository(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val LAST_USED_EMAIL = stringPreferencesKey(LAST_USED_EMAIL_KEY)
    }

    suspend fun getLastUsedEmail() =
        dataStore.data.firstOrNull()?.get(PreferencesKeys.LAST_USED_EMAIL) ?: ""

    suspend fun updateLastUsedEmail(email: String) {
        updatePreference(email, PreferencesKeys.LAST_USED_EMAIL)
    }

    private suspend fun <T> updatePreference(value: T, key: Preferences.Key<T>) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    companion object {
        private const val LAST_USED_EMAIL_KEY = "last_used_email"
    }
}