package org.d3if3152.riconvert.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "preferences"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = PREFERENCES_NAME
)

class SettingsDataStore(prefDataStore: DataStore<Preferences>){
    private val IS_DOLLAR = booleanPreferencesKey("is_dollar")

    val preferenceFlow: Flow<Boolean> = prefDataStore.data
        .catch { emit(emptyPreferences()) }
        .map { it[IS_DOLLAR] ?: true }

    suspend fun saveKurs(isDollar: Boolean, context: Context){
        context.dataStore.edit { it[IS_DOLLAR] = isDollar }
    }
}