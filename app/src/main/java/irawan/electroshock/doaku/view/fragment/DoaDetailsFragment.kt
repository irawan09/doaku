package irawan.electroshock.doaku.view.fragment

import irawan.electroshock.doaku.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import irawan.electroshock.doaku.model.DatabaseModel
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import com.airbnb.lottie.compose.*


@Composable
fun DoaDetailsFragment(navController: NavController, databaseModel: DatabaseModel){

    var isPlaying by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.muslim))

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = isPlaying,
        speed = speed,
        restartOnPlay = false)

    Column(modifier = Modifier
        .fillMaxWidth()) {
        OutlinedButton(border = BorderStroke(0.dp, Color.Transparent),
            onClick = { navController.navigate("DoaListFragment") },
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentSize(Alignment.TopStart)) {
            Icon(imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = null)
        }
        Box(modifier = Modifier
            .padding(vertical = 8.dp)
            .wrapContentSize(Alignment.Center)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)) {

                Text(text = databaseModel.doa, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentSize(Alignment.Center),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold)

                Text(text = databaseModel.ayat, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentSize(Alignment.TopEnd),
                    fontSize = 22.sp,
                    fontWeight = FontWeight(10))

                Text(text = databaseModel.latin, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .wrapContentSize(Alignment.TopStart),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic)

                Text(text = databaseModel.artinya, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .wrapContentSize(Alignment.TopStart),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal)
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Transparent)
                .padding(vertical = 8.dp)
        ){
            LottieAnimation(composition = composition,
                progress = progress,
                modifier = Modifier
                    .size(400.dp)
                    .padding(vertical = 16.dp)
                    .wrapContentSize(Alignment.BottomCenter))
        }
    }
}