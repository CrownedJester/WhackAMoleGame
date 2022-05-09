package com.crownedjester.soft.whackamolegame.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.crownedjester.soft.whackamolegame.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = Constants.DATASTORE_NAME)

class DataStoreManager @Inject constructor(@ApplicationContext appContext: Context) :
    DataStoreRepository {

    private val dataStore = appContext.dataStore

    override val record: Flow<Int>
        get() = dataStore.getValueAsFlow(
            Constants.RECORD_KEY,
            0
        )


    override suspend fun setRecord(record: Int) {
        dataStore.setValue(Constants.RECORD_KEY, record)
    }

    private suspend fun <T> DataStore<Preferences>.setValue(
        key: Preferences.Key<T>,
        value: T
    ) {
        this.edit { prefs ->
            prefs[key] = value
        }

    }

    private fun <T> DataStore<Preferences>.getValueAsFlow(
        key: Preferences.Key<T>,
        defaultValue: T
    ): Flow<T> {
        return this.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { prefs ->
            prefs[key] ?: defaultValue
        }
    }

}