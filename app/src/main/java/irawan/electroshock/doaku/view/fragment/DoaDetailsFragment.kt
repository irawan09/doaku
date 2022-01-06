package irawan.electroshock.doaku.view

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import irawan.electroshock.doaku.model.DatabaseModel

@Composable
fun DoaDetailsFragment(navController: NavController, databaseModel: DatabaseModel){
    Log.d("Data", databaseModel.toString())
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)
        .wrapContentSize(Alignment.Center)) {
        Box(modifier = Modifier
            .wrapContentSize(Alignment.Center)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentSize(Alignment.Center)) {

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
                    fontWeight = FontWeight(10)
                )

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
        Button(onClick = { navController.navigate("DoaListFragment") }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 150.dp)) {
            Text(text = "Kembali", modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center))
        }
    }

}