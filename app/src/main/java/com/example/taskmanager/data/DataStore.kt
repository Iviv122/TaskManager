package com.example.taskmanager.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.taskmanager.data.source.local.UpdateTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val DATASTORE_NAME = "app_settings"

private val Context.dataStore by preferencesDataStore(
    name = DATASTORE_NAME
)

private object PreferencesKeys {
    val UPDATE_TIME = longPreferencesKey("update_time")
}

public abstract class SettingsRepository {
    companion object {
        public fun getUpdateTime(context: Context): Flow<Long> {
            return context.dataStore.data.map { preferences ->
                preferences[PreferencesKeys.UPDATE_TIME] ?: UpdateTime.EveryMinute.updateTime
            }
        }

        public suspend fun setUpdateTime(value: Long, context: Context) {
            context.dataStore.edit { preferences ->
                preferences[PreferencesKeys.UPDATE_TIME] = value
            }
        }
    }

}