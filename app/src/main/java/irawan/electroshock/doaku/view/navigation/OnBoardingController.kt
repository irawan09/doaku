package irawan.electroshock.doaku.view.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.widget.onBoarding.OnboardingUi
import irawan.electroshock.doaku.view_model.DataViewModel

@ExperimentalCoilApi
@ExperimentalPagerApi
@OptIn(ExperimentalAnimationApi::class)
@ExperimentalComposeUiApi
@Composable
fun OnBoardingController(
    network : Boolean,
    dataViewModel: DataViewModel,
    data: List<DatabaseModel>
){
  val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "OnBoardingScreen"){
        composable("OnBoardingScreen"){
            OnboardingUi(navController)
        }
        composable("NavigationController"){
            NavigationController(network, dataViewModel, data)
        }
    }

}