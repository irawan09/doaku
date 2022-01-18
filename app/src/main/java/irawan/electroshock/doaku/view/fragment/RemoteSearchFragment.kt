package irawan.electroshock.doaku.view.fragment

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.google.gson.Gson
import irawan.electroshock.doaku.R
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.widget.Animation
import irawan.electroshock.doaku.view.widget.CardView
import irawan.electroshock.doaku.view.widget.SearchButton
import irawan.electroshock.doaku.view_model.DataViewModel

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun RemoteSearchFragment(context: Context, network: Boolean, navController: NavController, dataViewModel: DataViewModel, data: DatabaseModel){
    Scaffold(backgroundColor = Color.LightGray, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Animation(source = LottieCompositionSpec.RawRes(R.raw.mosque), size =400)
        Column(modifier = Modifier
            .fillMaxWidth()) {
            SearchButton(context, network, navController, dataViewModel)
            CardView(navController, data)
        }
    }
}
