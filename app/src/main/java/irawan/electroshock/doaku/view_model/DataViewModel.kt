package irawan.electroshock.doaku.view_model

import android.content.Context
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.di.DataRepository
import irawan.electroshock.doaku.model.SerializedModel
import irawan.electroshock.doaku.utils.Resource
import irawan.electroshock.doaku.view.use_case.DoaUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val doaUseCase: DoaUseCase,
    private val repository: DataRepository
) : ViewModel() {

    private var fetchRemoteData: LiveData<Resource<List<SerializedModel>>>? = null
    private var remoteResponseLiveData : LiveData<List<DatabaseModel>>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private lateinit var searchDatabaseDoa : LiveData<List<DatabaseModel>>

    init{
        viewModelScope.launch {
            fetchRemoteData = doaUseCase.getFetchRemoteUseCase()
            remoteResponseLiveData = doaUseCase.getDoaRemoteUseCase()
            databaseResponseData = doaUseCase.getDatabaseUseCase()
        }
    }

    fun getRemoteFetchData(): LiveData<Resource<List<SerializedModel>>>? {
        return fetchRemoteData
    }

    fun searchRemoteDoa(query: String): LiveData<List<DatabaseModel>>? {
        repository.searchDoa(query)
        return doaUseCase.getSearchUseCase()
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