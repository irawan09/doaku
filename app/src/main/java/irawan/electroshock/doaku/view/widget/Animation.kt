package irawan.electroshock.doaku.view.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*

@Composable
fun Animation(source : LottieCompositionSpec, size : Int){
    val isPlaying by remember {
        mutableStateOf(true)
    }
    val speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
//        spec = LottieCompositionSpec.RawRes(R.raw.muslim))
        spec = source)

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .padding(vertical = 8.dp)
    ){
        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .size(size.dp)
                .padding(vertical = 16.dp)
                .wrapContentSize(Alignment.BottomCenter))
    }
}