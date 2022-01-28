package irawan.electroshock.doaku.utils

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.HashSet

class NetworkMonitor(context : Context) : LiveData<Boolean>() {

    private lateinit var networkCallback : ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    private val validNetwork : MutableSet<Network> = HashSet()

    private fun checkValidNetworks(){
        postValue(validNetwork.size >0)
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback(){

        override fun onAvailable(network: Network){
            Log.d("Connectivity Manager", "${network}")
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability = networkCapabilities?.hasCapability(NET_CAPABILITY_INTERNET)
            Log.d("Connectivity Manager", "onAvailable: ${network}, $hasInternetCapability")
            if (hasInternetCapability == true){
                CoroutineScope(Dispatchers.IO).launch{
                    val hasInternet = InternetObserver .execute(network.socketFactory)
                    if (hasInternet){
                        Log.d("Connectivity Manager", "onAvailable: adding network. ${network}")
                        validNetwork.add(network)
                        checkValidNetworks()
                    }
                }
            }
        }

        override fun onLost(network: Network){
            Log.d("Connectivity Manager", "onLost: ${network}")
            validNetwork.remove(network)
            checkValidNetworks()
        }
    }
}