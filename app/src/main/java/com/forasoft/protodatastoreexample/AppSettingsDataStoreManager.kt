package com.forasoft.protodatastoreexample

import android.content.Context
import com.forasoft.protodatastoreexample.SettingsSerializer.settingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsDataStoreManager(private val context: Context) {
    private val dataStore
        get() = context.settingsDataStore

    val appSettingsFlow: Flow<AppSettings> = dataStore.data.map { preferences ->
        val background = when(preferences.background) {
            AppSettingsPreferences.Background.DARK_BACKGROUND -> AppBackground.DARK
            AppSettingsPreferences.Background.LIGHT_BACKGROUND -> AppBackground.LIGHT
            else -> null
        }

        val textColor = when(preferences.textColor) {
            AppSettingsPreferences.TextColor.DARK_TEXT_COLOR -> AppTextColor.DARK
            AppSettingsPreferences.TextColor.LIGHT_TEXT_COLOR -> AppTextColor.LIGHT
            else -> null
        }

        val textSize = preferences.textSize

        AppSettings(background, textColor, textSize)
    }

    suspend fun updateBackground(appBackground: AppBackground) {
        val background = when(appBackground) {
            AppBackground.DARK -> AppSettingsPreferences.Background.DARK_BACKGROUND
            AppBackground.LIGHT -> AppSettingsPreferences.Background.LIGHT_BACKGROUND
        }

        dataStore.updateData { currentAppSettings ->
            currentAppSettings
                .toBuilder()
                .setBackground(background)
                .build()
        }
    }

    suspend fun updateTextColor(appTextColor: AppTextColor) {
        val textColor = when(appTextColor) {
            AppTextColor.DARK -> AppSettingsPreferences.TextColor.DARK_TEXT_COLOR
            AppTextColor.LIGHT -> AppSettingsPreferences.TextColor.LIGHT_TEXT_COLOR
        }

        dataStore.updateData { currentAppSettings ->
            currentAppSettings
                .toBuilder()
                .setTextColor(textColor)
                .build()
        }
    }

    suspend fun updateTextSize(appTextSize: Int) {
        dataStore.updateData { currentAppSettings ->
            currentAppSettings
                .toBuilder()
                .setTextSize(appTextSize)
                .build()
        }
    }
}