package irawan.electroshock.doaku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.ui.theme.DoakuTheme
import irawan.electroshock.doaku.utils.NetworkMonitor
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view.NavigationController
import irawan.electroshock.doaku.view_model.DataViewModel

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
                dataViewModel.getRemoteResponseLiveData()?.observe(this, { data ->
                    setContent {
                        DoakuTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                    NavigationController(this, network, data)

                            }
                        }
                    }
                })
            } else{
                Toast.makeText(this,"Data from Database !!", Toast.LENGTH_LONG).show()
                dataViewModel.getDatabaseResponseLiveData()?.observe(this, {
                    setContent {
                        DoakuTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                    NavigationController(this, network, it)
                            }
                        }
                    }
                })
            }
        })
    }
}