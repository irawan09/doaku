package irawan.electroshock.doaku.api

import irawan.electroshock.doaku.model.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DataService {
    @GET("/all")
    suspend fun getAllData() : Response<JSONObject>

    @GET("/search/{search}")
    suspend fun getSearchByDoa(@Path(value = "search", encoded = true) name: String?): Response<JSONObject>
}