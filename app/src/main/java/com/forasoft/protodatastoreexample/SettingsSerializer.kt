package com.forasoft.protodatastoreexample

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<AppSettingsPreferences> {
    val Context.settingsDataStore: DataStore<AppSettingsPreferences> by dataStore(
        fileName = "app_settings",
        serializer = SettingsSerializer
    )

    override val defaultValue: AppSettingsPreferences
        get() = AppSettingsPreferences.getDefaultInstance()

    override fun readFrom(input: InputStream): AppSettingsPreferences {
        try {
            return AppSettingsPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: AppSettingsPreferences, output: OutputStream) = t.writeTo(output)
}