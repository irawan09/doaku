package irawan.electroshock.doaku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.utils.NetworkMonitor
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view.navigation.NavigationController
import irawan.electroshock.doaku.view.navigation.OnBoardingController
import irawan.electroshock.doaku.view.theme.DoakuTheme
import irawan.electroshock.doaku.view.widget.ShowAlertDialog
import irawan.electroshock.doaku.view_model.DataViewModel
import irawan.electroshock.doaku.view_model.OnBoardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalComposeUiApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dataViewModel : DataViewModel by viewModels()
    private val onBoardViewModel : OnBoardViewModel by viewModels()
    private lateinit var networkMonitor : NetworkMonitor

    @OptIn(ExperimentalCoilApi::class)
    public override fun onCreate(savedInstanceState: Bundle?) {
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
                        onBoardViewModel.retrieveOnBoarding().observe(this, { onBoarding ->
                            if (onBoarding == true){
                                layout(network, remote)
                            } else {
                                onBoardingScreen(network, onBoardViewModel, remote)
                            }
                        })
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
                Surface(color = Color.White) {
                    ShowAlertDialog()
                }
            }
        }
    }

    @ExperimentalCoilApi
    @ExperimentalPagerApi
    private fun onBoardingScreen(
        network : Boolean,
        onBoardViewModel: OnBoardViewModel,
        data: List<DatabaseModel>
    ){
        setContent{
            DoakuTheme {
                Surface(color = Color.White) {
                    OnBoardingController(network, dataViewModel, onBoardViewModel, data)
                }

            }
        }
    }

    @ExperimentalCoilApi
    @ExperimentalAnimationApi
    @ExperimentalPagerApi
    private fun layout(network : Boolean, data: List<DatabaseModel>){
        setContent {
            DoakuTheme {
                Surface(color = Color.White) {
                    NavigationController(network, dataViewModel, data)
                }
            }
        }
    }
}