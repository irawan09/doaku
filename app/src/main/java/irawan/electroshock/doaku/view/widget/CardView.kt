package irawan.electroshock.doaku.view.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel

@Composable
fun CardView(navController: NavController, data: DatabaseModel){
    fun navigateToDetails(databaseModel: DatabaseModel) {
        val doaJson = Gson().toJson(databaseModel)
        navController.navigate("DoaDetailsFragment/$doaJson")
    }

    Card (shape = RoundedCornerShape(8.dp) ,elevation = 16.dp, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .wrapContentSize(Alignment.TopStart)
        .clickable {
            navigateToDetails(data)
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            CardPicture()
            CardText(data.doa)
        }
    }
}

@Composable
fun CardPicture(){
    Card(shape = CircleShape,
        border = BorderStroke(2.dp, Color.LightGray),
        elevation = 4.dp,
        modifier = Modifier.padding(8.dp)) {
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