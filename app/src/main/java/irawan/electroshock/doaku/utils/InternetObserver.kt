package irawan.electroshock.doaku.utils

import android.util.Log
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

object InternetObserver  {

    fun execute(socketFactory: SocketFactory) : Boolean{
        return try{
            Log.d("Connectivity Manager", "Pinging Google")
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8",53), 1500)
            socket.close()
            Log.d("Connectivity Manager", "Ping Succes")
            true
        }catch (e: IOException){
            Log.e("Connectivity Manager", "No Internet connection. ${e}")
            false
        }
    }
}