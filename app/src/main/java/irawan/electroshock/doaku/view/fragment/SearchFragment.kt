package irawan.electroshock.doaku.view.fragment

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

@Composable
fun SearchButton(network: Boolean) {
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
                }else{
                    Log.d("Database Search", dataSearch.text)
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