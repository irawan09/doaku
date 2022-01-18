package irawan.electroshock.doaku.view.fragment

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*
import com.google.gson.Gson
import irawan.electroshock.doaku.R
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.widget.SearchButton
import irawan.electroshock.doaku.view_model.DataViewModel



@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun DoaListFragment(context: Context, network: Boolean, navController: NavController, dataViewModel: DataViewModel, data: List<DatabaseModel> ){

    fun navigateToDetails(databaseModel: DatabaseModel) {
        val doaJson = Gson().toJson(databaseModel)
        navController.navigate("DoaDetailsFragment/$doaJson")
    }

    Scaffold(backgroundColor = Color(0xFFCECECE)) {
        AnimationBackground()
        Column(modifier = Modifier
            .fillMaxWidth()) {
            SearchButton(context, network, navController, dataViewModel)
            LazyColumn {
                items(data.size){ index ->
                    Card (elevation = 16.dp, modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .wrapContentSize(Alignment.TopStart)
                        .clickable {
                            navigateToDetails(data[index])
                        }) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            CardPicture()
                            CardText(data[index].doa)

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardPicture(){
    Card(shape = CircleShape) {
        Image(painter = rememberImagePainter(
            data = "https://freeislamiccalligraphy.com/wp-content/uploads/2013/06/Allah-Square-Kufic.jpg",
            imageLoader = LocalImageLoader.current,
            builder = {
                crossfade(true)
            }
        ),
            contentDescription = null,
            modifier = Modifier
                .height(64.dp)
                .width(64.dp))
    }
}

@Composable
fun CardText(doaName : String){
    Column(modifier = Modifier
        .fillMaxWidth()) {
        Text(text = doaName)
    }
}

@Composable
fun AnimationBackground(){
    val isPlaying by remember {
        mutableStateOf(true)
    }
    val speed by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.mosque))

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
        LottieAnimation(composition = composition,
            progress = progress,
            modifier = Modifier
                .size(400.dp)
                .padding(vertical = 16.dp)
                .wrapContentSize(Alignment.BottomCenter))
    }
}