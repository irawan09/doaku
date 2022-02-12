package irawan.electroshock.doaku.view.fragment

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.airbnb.lottie.compose.LottieCompositionSpec
import irawan.electroshock.doaku.R
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.widget.Animation
import irawan.electroshock.doaku.view.widget.CardView
import irawan.electroshock.doaku.view.widget.SearchButton
import irawan.electroshock.doaku.view_model.DataViewModel

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@Composable
fun RemoteSearchFragment(context: Context, network: Boolean, navController: NavController, dataViewModel: DataViewModel, data: List<DatabaseModel>){
    Scaffold(backgroundColor = Color.LightGray, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Animation(source = LottieCompositionSpec.RawRes(R.raw.mosque), size =400)
        Column(modifier = Modifier
            .fillMaxWidth()) {
            SearchButton(context, network, navController, dataViewModel)
            LazyColumn {
                items(data.size){ index ->
                    CardView(navController = navController, data = data[index])
                }
            }
        }
    }
}
