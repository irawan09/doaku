package irawan.electroshock.doaku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.ui.theme.DoakuTheme
import irawan.electroshock.doaku.utils.NetworkMonitor
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view.navigation.NavigationController
import irawan.electroshock.doaku.view.widget.ShowAlertDialog
import irawan.electroshock.doaku.view.widget.onBoarding.OnboardingUi
import irawan.electroshock.doaku.view_model.DataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dataViewModel : DataViewModel by viewModels<DataViewModel>()
    private lateinit var networkMonitor : NetworkMonitor

    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.setLifeCycleOwner(this)
        val inetCheck = Utils.hasNetwork(this)
        if (!inetCheck){
            CoroutineScope(Dispatchers.Main).launch{
                alert()
                delay(4000L)
                finish()
            }
        }else{
            networkMonitor = NetworkMonitor(this)
            networkMonitor.observe(this, { network ->
                if(network == true){
                    dataViewModel.getRemoteResponseLiveData()?.observe(this, { remote ->
                        layout(network, remote)
                    })
                } else{
                    dataViewModel.getDatabaseResponseLiveData()?.observe(this, { db ->
                        layout(network, db)
                    })
                }
            })
        }
    }

    private fun alert(){
        setContent {
            DoakuTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ShowAlertDialog()
                }
            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    private fun layout(network : Boolean, data: List<DatabaseModel>){
        setContent {
            DoakuTheme {
                Surface(color = MaterialTheme.colors.background) {
//                    OnboardingUi()
                    NavigationController(network, dataViewModel, data)
                }
            }
        }
    }
}