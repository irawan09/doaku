package irawan.electroshock.doaku.view.fragment

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import irawan.electroshock.doaku.database.DoaDatabaseFactory
import irawan.electroshock.doaku.utils.Utils

@ExperimentalComposeUiApi
@Composable
fun SearchFragment(context: Context, network: Boolean, navController: NavController ) {
    var dataSearch by remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column() {
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
                    if (network == true){
                        Log.d("Data Search",dataSearch.text)
//                        navController.navigate("SearchFragment")

                    }else{
                        searchDataFromDb(dataSearch.text, context, navController)

                    }
                }),
//            onImeActionPerformed = {action, softKeyboardController ->
//
//            },
            label = { Text(text = "Search") },
            placeholder = { Text(text = "Enter your search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

    }


    return
}

fun searchDataFromDb(search: String, context : Context, navController: NavController){
    val search ="%$search%"
    DoaDatabaseFactory.getDatabaseInstance(context).doaDao().getDoaName(search).observe(Utils.getLifeCycleOwner(), {
        Log.d("Database Search", it.toString())
        navController.navigate("SearchFragment")
    })
}
