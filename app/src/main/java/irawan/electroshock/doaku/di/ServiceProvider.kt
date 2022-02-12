package irawan.electroshock.doaku.di

import android.content.Context
import com.google.gson.GsonBuilder
import dagger.hilt.android.qualifiers.ApplicationContext
import irawan.electroshock.doaku.api.DataService
import irawan.electroshock.doaku.utils.Utils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceProvider @Inject constructor(@ApplicationContext private var context : Context) {

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
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun createService() : DataService = Retrofit.Builder()
        .baseUrl("https://tanyadoa-api.herokuapp.com")
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()
        .create(DataService::class.java)
}