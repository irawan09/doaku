package irawan.electroshock.doaku.view_model

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.di.DataRepository
import irawan.electroshock.doaku.utils.datastore.DataStorePreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(private val dataRepository: DataRepository) : ViewModel() {

    private var remoteResponseLiveData : LiveData<List<DatabaseModel>>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private lateinit var searchDatabaseDoa : LiveData<List<DatabaseModel>>

    init{
        viewModelScope.launch {
            remoteResponseLiveData = dataRepository.getDoaResponseLiveData()
            databaseResponseData = dataRepository.getDatabaseResponseLiveData()
        }
    }


    fun searchRemoteDoa(query: String): LiveData<DatabaseModel>? {
        dataRepository.searchDoa(query)
        return dataRepository.getDoaResponseSearchLiveData()
    }

    fun searchDatabaseDoa(context: Context, query: String) : LiveData<List<DatabaseModel>> {
        val search ="%${query}%"
        searchDatabaseDoa = DoaDatabaseFactory.getDatabaseInstance(context).doaDao().getDoaName(search)
        return searchDatabaseDoa
    }

    fun getRemoteResponseLiveData() : LiveData<List<DatabaseModel>>? {
        return remoteResponseLiveData
    }

    fun getDatabaseResponseLiveData() : LiveData<List<DatabaseModel>>?{
        return databaseResponseData
    }
}