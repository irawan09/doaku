package irawan.electroshock.doaku.view.widget

import android.view.animation.OvershootInterpolator
import irawan.electroshock.doaku.R
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    val scale = remember {
     Animatable(0f)
    }
    
    LaunchedEffect(key1 = true){
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(4000L)
        navController.navigate("DoaListFragment")

    }

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