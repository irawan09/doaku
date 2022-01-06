package irawan.electroshock.doaku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.ui.theme.DoakuTheme
import irawan.electroshock.doaku.utils.NetworkMonitor
import irawan.electroshock.doaku.view.NavigationController
import irawan.electroshock.doaku.view.fragment.SearchButton
import irawan.electroshock.doaku.view_model.DataViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dataViewModel : DataViewModel by viewModels<DataViewModel>()
    private lateinit var networkMonitor : NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkMonitor = NetworkMonitor(this)
        networkMonitor.observe(this, { network ->
            if(network == true){
                dataViewModel.getRemoteResponseLiveData()?.observe(this, { data ->
                    setContent {
                        DoakuTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                Column(modifier = Modifier
                                    .fillMaxWidth()) {
                                    SearchButton()
                                    NavigationController(data)
                                }
                            }
                        }
                    }
                })
            } else{
                Toast.makeText(this,"Data from Database !!", Toast.LENGTH_LONG).show()
                val db = DoaDatabaseFactory.getDatabaseInstance(context = this).doaDao().getAllDoa()
                db.observe(this, {
                    setContent {
                        DoakuTheme {
                            Surface(color = MaterialTheme.colors.background) {
                                NavigationController(it)
                            }
                        }
                    }
                })
            }
        })
    }
}