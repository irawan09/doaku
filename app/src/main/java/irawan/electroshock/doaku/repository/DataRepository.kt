package irawan.electroshock.doaku.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import irawan.electroshock.doaku.api.ServiceProvider
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataRepository(context: Context) {
    private var doaResponseLiveData : MutableLiveData<List<DatabaseModel>>? = null
    private var doaResponseSearchLiveData : MutableLiveData<List<DatabaseModel>>? = null
    private var databaseResponseData : LiveData<List<DatabaseModel>>? = null
    private var doaArray : ArrayList<DatabaseModel> = ArrayList()

    init {
        doaResponseLiveData = MutableLiveData<List<DatabaseModel>>()
        doaResponseSearchLiveData = MutableLiveData<List<DatabaseModel>>()
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

    fun SearchDoa(context: Context, query : String){
        val service = ServiceProvider(context).createService()
        CoroutineScope(Dispatchers.IO).launch{
            val response = service.geSearchByDoa(query)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val data = response.body()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(data)
                    Log.d("Pretty JSON response ", prettyJson)
                } else {
                    Log.e("Status", "Retrofit Error ${response.code()}")
                }
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