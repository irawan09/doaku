package irawan.electroshock.doaku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import dagger.hilt.android.AndroidEntryPoint
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.ui.theme.DoakuTheme
import irawan.electroshock.doaku.utils.NetworkMonitor
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view.navigation.NavigationController
import irawan.electroshock.doaku.view_model.DataViewModel

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dataViewModel : DataViewModel by viewModels<DataViewModel>()
    private lateinit var networkMonitor : NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setLifeCycleOwner(this)
        networkMonitor = NetworkMonitor(this)
        networkMonitor.observe(this, { network ->
            if(network == true){
                dataViewModel.getRemoteResponseLiveData()?.observe(this, { remote ->
                    layout(network, remote)
                })
            } else{
                Toast.makeText(this,"Ambil data pada database !!", Toast.LENGTH_SHORT).show()
                dataViewModel.getDatabaseResponseLiveData()?.observe(this, { db ->
                    layout(network, db)
                })
            }
        })
    }

    private fun layout(network : Boolean, data: List<DatabaseModel>){
        setContent {
            DoakuTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavigationController(network, dataViewModel, data)
                }
            }
        }
    }
}