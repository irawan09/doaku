package irawan.electroshock.doaku.api

import irawan.electroshock.doaku.model.SerializedModel
import retrofit2.Response
import retrofit2.http.GET

interface DataService {
    @GET("/api")
    suspend fun getAllData() : Response<List<SerializedModel>>
}