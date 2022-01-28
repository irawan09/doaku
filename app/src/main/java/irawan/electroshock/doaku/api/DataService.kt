package irawan.electroshock.doaku.api

import irawan.electroshock.doaku.model.SerializedModel
import retrofit2.Response
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.http.Path


interface DataService {
    @GET("/api")
    suspend fun getAllData() : Response<List<SerializedModel>>

    @GET("/api/doa/{search}")
    suspend fun geSearchByDoa(@Path(value = "search", encoded = true) name: String?): Response<SerializedModel>
}