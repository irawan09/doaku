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

    init{
        viewModelScope.launch {
            dataRepository = DataRepository(application)
            remoteResponseLiveData = dataRepository.getDoaResponseLiveData()

            databaseResponseData = DoaDatabaseFactory.getDatabaseInstance(context = application).doaDao().getAllDoa()
        }
    }

    fun getRemoteResponseLiveData() : LiveData<List<DatabaseModel>>? {
        return remoteResponseLiveData
    }

    fun getDatabaseResponseLiveData() : LiveData<List<DatabaseModel>>?{
        return databaseResponseData
    }
}