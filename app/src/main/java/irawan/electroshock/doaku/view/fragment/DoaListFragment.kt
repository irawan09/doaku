package irawan.electroshock.doaku.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.fragment.SearchFragment

@ExperimentalCoilApi
@Composable
fun DoaListFragment(context: Context, network: Boolean, navController: NavController, data: List<DatabaseModel> ){

    fun navigateToDetails(databaseModel: DatabaseModel) {
        val doaJson = Gson().toJson(databaseModel)
        navController.navigate("DoaDetailsFragment/$doaJson")
    }

    Column(modifier = Modifier
        .fillMaxWidth()) {
        SearchFragment(context, network, navController)
        LazyColumn {
            items(data.size){ index ->
                Card ( elevation = 8.dp, modifier = Modifier
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
                        Image(painter = rememberImagePainter(
                            data = "https://freeislamiccalligraphy.com/wp-content/uploads/2013/06/Allah-Square-Kufic.jpg",
                            builder = {}),
                            contentDescription = null,
                            modifier = Modifier
                                .height(64.dp)
                                .width(64.dp))

                        Column(modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()) {
                            val doaName = data[index].doa
                            Text(text = doaName)
                        }
                    }
                }
            }
        }
    }
}