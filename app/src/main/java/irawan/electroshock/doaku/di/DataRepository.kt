package irawan.electroshock.doaku.di

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository @Inject constructor(
    @ApplicationContext context: Context,
    private val serviceProvider: ServiceProvider) {

    private var doaResponseLiveData : MutableLiveData<List<DatabaseModel>>? = null
    private var doaResponseSearchLiveData : MutableLiveData<DatabaseModel>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private var doaArray : ArrayList<DatabaseModel> = ArrayList()

    init {
        doaResponseLiveData = MutableLiveData<List<DatabaseModel>>()
        doaResponseSearchLiveData = MutableLiveData<DatabaseModel>()
        loadAllData(context)
        databaseResponseData = DoaDatabaseFactory.getDatabaseInstance(context).doaDao().getAllDoa()
    }

    private fun loadAllData(context: Context){
        val service = ServiceProvider(context).createService()
        val db = DoaDatabaseFactory.getDatabaseInstance(context = context)
        CoroutineScope(Dispatchers.IO).launch{
            val response = service.getAllData()
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val data = response.body()
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
                    }
                }
                else{
                    Log.e("Status", "Retrofit Error ${response.code()}")
                }
            }
        }
    }

    fun searchDoa(query : String){
        val service = serviceProvider.createService()
        CoroutineScope(Dispatchers.IO).launch{
            val response = service.geSearchByDoa(query)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        val id = data.id ?: "N/A"
                        val doa = data.doa ?: "N/A"
                        val ayat = data.ayat ?: "N/A"
                        val latin = data.latin ?: "N/A"
                        val artinya = data.artinya ?: "N/A"

                        val dataDoaSearch =
                            DatabaseModel(
                                id = id,
                                doa = doa,
                                ayat = ayat,
                                latin = latin,
                                artinya = artinya
                            )
                        doaResponseSearchLiveData?.postValue(dataDoaSearch)
                    }
                } else {
                    Log.e("Status", "Retrofit Error ${response.code()}")
                }
            }
        }
    }

    fun getDoaResponseSearchLiveData(): LiveData<DatabaseModel>? {
        return doaResponseSearchLiveData
    }

    fun getDoaResponseLiveData() : LiveData<List<DatabaseModel>>? {
        return doaResponseLiveData
    }

    fun getDatabaseResponseLiveData() : LiveData<List<DatabaseModel>>?{
        return databaseResponseData
    }
}