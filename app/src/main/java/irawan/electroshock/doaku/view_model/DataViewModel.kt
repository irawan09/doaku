package irawan.electroshock.doaku.view_model

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import dagger.hilt.android.qualifiers.ApplicationContext
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.repository.DataRepository
import kotlinx.coroutines.launch

class DataViewModel @ViewModelInject constructor(@ApplicationContext application : Context) : ViewModel() {

    private lateinit var dataRepository : DataRepository
    private var remoteResponseLiveData : LiveData<List<DatabaseModel>>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private lateinit var searchDatabaseDoa : LiveData<List<DatabaseModel>>

    init{
        viewModelScope.launch {
            dataRepository = DataRepository(application)
            remoteResponseLiveData = dataRepository.getDoaResponseLiveData()
            databaseResponseData = dataRepository.getDatabaseResponseLiveData()

            fun searchRemoteDoa(context: Context, query: String): LiveData<DatabaseModel>? {
                dataRepository.SearchDoa(context, query)
                return dataRepository.getDoaResponseSearchLiveData()
            }
        }
    }

    fun searchRemoteDoa(context: Context, query: String): LiveData<DatabaseModel>? {
        dataRepository.SearchDoa(context, query)
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