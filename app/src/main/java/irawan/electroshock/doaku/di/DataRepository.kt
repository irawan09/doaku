package irawan.electroshock.doaku.di

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val serviceProvider: ServiceProvider) {

    private var doaResponseLiveData : MutableLiveData<List<DatabaseModel>>? = null
    private var doaResponseSearchLiveData : MutableLiveData<List<DatabaseModel>>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private var doaArray : ArrayList<DatabaseModel> = ArrayList()
    private var remoteDoaArray : ArrayList<DatabaseModel> = ArrayList()

    init {
        doaResponseLiveData = MutableLiveData<List<DatabaseModel>>()
        doaResponseSearchLiveData = MutableLiveData<List<DatabaseModel>>()
        loadAllData(context)
        databaseResponseData = DoaDatabaseFactory.getDatabaseInstance(context).doaDao().getAllDoa()
    }

    private fun loadAllData(context: Context){
        val service = serviceProvider.createService()
        val db = DoaDatabaseFactory.getDatabaseInstance(context = context)
        CoroutineScope(Dispatchers.IO).async(Dispatchers.Main){
            val response = service.getAllData()
                if (response.isSuccessful){
                    val data = response.body()?.data
                    if(data != null){
                        for (i in 0 until data.count()){
                            val id = data[i].id ?: "N/A"
                            val doa = data[i].doa ?: "N/A"
                            val ayat = data[i].ayat ?: "N/A"
                            val latin = data[i].latin ?: "N/A"
                            val artinya = data[i].artinya ?: "N/A"

                            val dataDoa =
                                DatabaseModel(
                                    id = id,
                                    doa = doa,
                                    ayat = ayat,
                                    latin = latin,
                                    artinya = artinya
                                )
                            doaArray.add(dataDoa)
                            db.doaDao().insertDoa(dataDoa)
                            doaResponseLiveData?.postValue(doaArray)
                        }
                    } else {
                        Log.e("Status", "Retrofit Null data}")
                    }
                }
                else{
                    Log.e("Status", "Retrofit Error ${response.code()}")
                }
        }
    }

    fun searchDoa(query : String){
        val service = serviceProvider.createService()
        CoroutineScope(Dispatchers.IO).async(Dispatchers.Main){
            val response = service.getSearchByDoa(query)
                if (response.isSuccessful){
                    val data = response.body()?.data
                    if (data != null){
                        for (i in 0 until data.count()){
                            val id = data[i].id ?: "N/A"
                            val doa = data[i].doa ?: "N/A"
                            val ayat = data[i].ayat ?: "N/A"
                            val latin = data[i].latin ?: "N/A"
                            val artinya = data[i].artinya ?: "N/A"

                            val dataDoaSearch =
                                DatabaseModel(
                                    id = id,
                                    doa = doa,
                                    ayat = ayat,
                                    latin = latin,
                                    artinya = artinya
                                )
                            remoteDoaArray.add(dataDoaSearch)
                            doaResponseSearchLiveData?.postValue(remoteDoaArray)
                        }
                    } else {
                        Log.e("Status", "Retrofit Null data}")
                    }
                } else {
                    Log.e("Status", "Retrofit Error ${response.code()}")
                }
        }
    }

    fun getDoaResponseSearchLiveData(): LiveData<List<DatabaseModel>>? {
        return doaResponseSearchLiveData
    }

    fun getDoaResponseLiveData() : LiveData<List<DatabaseModel>>? {
        return doaResponseLiveData
    }

    fun getDatabaseResponseLiveData() : LiveData<List<DatabaseModel>>?{
        return databaseResponseData
    }
}