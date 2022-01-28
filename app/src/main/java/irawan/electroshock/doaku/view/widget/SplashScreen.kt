package irawan.electroshock.doaku.view.widget

import irawan.electroshock.doaku.R
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(925L)
        navController.popBackStack()
        navController.navigate("DoaListFragment")
    }
    Animation(source = LottieCompositionSpec.RawRes(R.raw.logo), size = 200)
}