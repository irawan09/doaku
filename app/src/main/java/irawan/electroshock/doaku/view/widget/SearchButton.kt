package irawan.electroshock.doaku.view.widget

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.utils.Utils
import irawan.electroshock.doaku.view_model.DataViewModel

@ExperimentalComposeUiApi
@Composable
fun SearchButton(context: Context, network: Boolean, navController: NavController, dataViewModel: DataViewModel) {
    var dataSearch by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    fun getRemoteSearchData(databaseModel: DatabaseModel) {
        val doaJson = Gson().toJson(databaseModel)
        navController.navigate("RemoteSearchFragment/$doaJson")
    }

    fun getSearchDatabase(databaseModel: List<DatabaseModel>) {
        val doaJson = Gson().toJson(databaseModel)
        navController.navigate("SearchFragment/$doaJson")
    }

    Card(shape = RoundedCornerShape(8.dp),
//        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .padding(8.dp)) {
        Column(Modifier.background(Color.White)) {
            OutlinedTextField(
                value = dataSearch,
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                onValueChange = { search ->
                    dataSearch = search
                },
                keyboardActions = KeyboardActions(
                    onSearch = { KeyboardActions(
                        onSearch = {keyboardController?.hide()})
                        if (network){
                            val observerData = dataViewModel.searchRemoteDoa(dataSearch.text)
                            observerData?.observe(Utils.getLifeCycleOwner(), { data ->
                                getRemoteSearchData(data)
                            })
                        }else{
                            val observerData = dataViewModel.searchDatabaseDoa(context, dataSearch.text)
                            observerData.observe(Utils.getLifeCycleOwner(), { data ->
                                getSearchDatabase(data)
                            })
                        }
                    }),
                label = { Text(text = "Search") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Blue,
                    unfocusedBorderColor = Gray),
                placeholder = { Text(text = "Enter your search") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}