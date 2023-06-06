package com.example.newsapp.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.newsapp.data.datastore.ProviderDataStore.Companion.PREFS_NAME
import com.example.newsapp.data.remote.Filter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFS_NAME)

class ProviderDataStore(context: Context) {
    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSelectedFilter(filter: Filter) {
        dataStore.edit { preferences ->
            preferences[FILTER_KEY] = filter.filterName
        }
    }

    fun getSelectedFilter(): Flow<Filter> {
        return dataStore.data.map { preferences ->
            val filterName = preferences[FILTER_KEY].orEmpty()
            Filter(filterName)
        }
    }

    companion object {
        val FILTER_KEY = stringPreferencesKey("filter")
        const val PREFS_NAME = "filters_prefs"
    }


}