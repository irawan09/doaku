package irawan.electroshock.doaku.api

import android.content.Context
import com.google.gson.GsonBuilder
import irawan.electroshock.doaku.utils.Utils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceProvider(context: Context) {
    private val  cacheSize = (5*1024*1024).toLong()
    private val myCache = Cache(context.cacheDir, cacheSize)

    private val okHttpClient = OkHttpClient.Builder()
        .cache(myCache)
        .addInterceptor { chain ->
            var request = chain.request()
            request =
                if (Utils.hasNetwork(context = context))
                    request.newBuilder().header("Cache-Control","public, max-age="+5).build()
                else
                    request.newBuilder().header("Cache-Control","public, only-if-cached, max-stale"+60*60*24*7).build()
            chain.proceed(request)
        }
        .build()

    fun createService() : DataService = Retrofit.Builder()
        .baseUrl("https://doa-doa-api-ahmadramadhan.fly.dev")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()
        .create(DataService::class.java)
}