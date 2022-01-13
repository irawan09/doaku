package irawan.electroshock.doaku.view.widget

import irawan.electroshock.doaku.R
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun AnimatedSplashScreen(navController: NavController){
    LaunchedEffect(key1 = true){
        delay(3000L)
        navController.popBackStack()
        navController.navigate("DoaListFragment")
    }
    SplashScreen()
}

@Composable
fun SplashScreen(){

    val isPlaying by remember {
        mutableStateOf(true)
    }
    val speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.electroshock))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false)

    Box(contentAlignment = Alignment.Center ,
        modifier = Modifier.fillMaxSize()){
            LottieAnimation(
                composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(200.dp)
                    .padding(vertical = 16.dp)
                    .wrapContentSize(Alignment.BottomCenter))
    }

}