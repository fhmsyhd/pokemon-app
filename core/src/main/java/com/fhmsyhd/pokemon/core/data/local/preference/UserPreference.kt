package com.fhmsyhd.pokemon.core.data.local.preference

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fhmsyhd.pokemon.core.util.Constant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        private const val PREF_NAME = Constant.PREF_NAME
        private val Context.dataStore by preferencesDataStore(name = PREF_NAME)

        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        private val KEY_USERNAME = stringPreferencesKey("username")
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[KEY_IS_LOGGED_IN] ?: false }

    val username: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[KEY_USERNAME] }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = loggedIn
        }
    }

    suspend fun setUsername(name: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USERNAME] = name
        }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}