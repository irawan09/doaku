package irawan.electroshock.doaku.view.fragment

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.google.gson.Gson
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view.DoaListFragment

@Composable
fun SearchButton(context: Context, network: Boolean, navController: NavController) {
    var dataSearch by remember { mutableStateOf(TextFieldValue("")) }
    return Column() {
        OutlinedTextField(
            value = dataSearch,
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { search ->
                dataSearch = search
                if (network == true){
                    Log.d("Data Search",dataSearch.text)
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        navController.navigate("SearchFragment")
//                    }, 10000)

                }else{
                    Log.d("Database Search", dataSearch.text)
                    searchDataFromDb(dataSearch.text, context, navController)

                }
            },
            label = { Text(text = "Search") },
            placeholder = { Text(text = "Enter your search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@ExperimentalCoilApi
@Composable
fun SearchFragment(context: Context, network: Boolean, navController: NavController, databaseModel: List<DatabaseModel>){
        DoaListFragment(context, network, navController, databaseModel)
}

fun searchDataFromDb(search: String, context : Context, navController: NavController){
    val data ="%$search%"
    fun listOfSearch(databaseModel: List<DatabaseModel>) {
        val doaJson = Gson().toJson(databaseModel)
//        navController.navigate("SearchFragment/$doaJson")
    }
    DoaDatabaseFactory.getDatabaseInstance(context).doaDao().getDoaName(data).observe(Utils.getLifeCycleOwner(), {
        Log.d("Database Search", it.toString())
        listOfSearch(it)
//        Handler(Looper.getMainLooper()).postDelayed({
//            navController.navigate("SearchFragment") }, 10000)
    })
}
