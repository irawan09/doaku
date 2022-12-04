package irawan.electroshock.doaku.view.use_case

import androidx.lifecycle.LiveData
import irawan.electroshock.doaku.di.DataRepository
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.model.SerializedModel
import irawan.electroshock.doaku.utils.Resource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DoaUseCase @Inject constructor(private val repository: DataRepository) {

    fun getDoaRemoteUseCase(): LiveData<List<DatabaseModel>>? = repository.getDoaResponseLiveData()
    fun getFetchRemoteUseCase(): LiveData<Resource<List<SerializedModel>>>? = repository.fetchRemoteData()
    fun getDatabaseUseCase(): LiveData<List<DatabaseModel>>? = repository.getDatabaseResponseLiveData()
    fun getSearchUseCase(): LiveData<List<DatabaseModel>>? = repository.getDoaResponseSearchLiveData()
}