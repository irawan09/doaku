package irawan.electroshock.doaku.utils.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "irawan.electroshock.doaku")

class DataStorePreference @Inject constructor(@ApplicationContext private val context: Context) : Abstract {

    override suspend fun saveOnboarding(save:Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SHOW_COMPLETED] = save
        }
    }

    override suspend fun fetchOnboarding() = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.SHOW_COMPLETED] ?: false  }

    private object PreferencesKeys {
        val SHOW_COMPLETED = booleanPreferencesKey("show_completed")
    }
}