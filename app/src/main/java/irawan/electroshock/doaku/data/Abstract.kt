package irawan.electroshock.doaku.data

import kotlinx.coroutines.flow.Flow

interface Abstract {
    suspend fun saveOnboarding(save:Boolean)
    suspend fun fetchOnboarding(): Flow<Boolean>
}