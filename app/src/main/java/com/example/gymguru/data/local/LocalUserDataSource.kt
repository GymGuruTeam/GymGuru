package com.example.gymguru.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalUserDataSource @Inject constructor(
    private val context: Context
) {

    fun observeLocalUserName(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME_KEY]
    }

    fun observeLocalUserHeight(): Flow<Int?> = context.dataStore.data.map { preferences ->
        preferences[USER_HEIGHT_KEY]
    }

    fun observeLocalUserBirthday(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USER_BIRTHDAY_KEY]
    }

    fun observeLocalIsOnBoardingShown(): Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[SHOW_ON_BOARDING] ?: false
    }

    suspend fun setLocalUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = name
        }
    }

    suspend fun setLocalUserHeight(height: Int) {
        context.dataStore.edit { preferences ->
            preferences[USER_HEIGHT_KEY] = height
        }
    }

    suspend fun setLocalUserBirthday(date: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_BIRTHDAY_KEY] = date
        }
    }

    suspend fun setIsOnBoardingShown(isShown: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SHOW_ON_BOARDING] = isShown
        }
    }

    suspend fun clearLocalUserData() {
        context.dataStore.edit { it.clear() }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")

        private val SHOW_ON_BOARDING: Preferences.Key<Boolean> =
            booleanPreferencesKey("show_onboarding")

        private val USER_NAME_KEY: Preferences.Key<String> =
            stringPreferencesKey("user_name_key")

        private val USER_HEIGHT_KEY: Preferences.Key<Int> =
            intPreferencesKey("user_height_key")

        private val USER_BIRTHDAY_KEY: Preferences.Key<String> =
            stringPreferencesKey("user_birthday_key")
    }
}
