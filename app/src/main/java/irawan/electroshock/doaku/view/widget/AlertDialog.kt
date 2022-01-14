package irawan.electroshock.doaku.view.widget

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import irawan.electroshock.doaku.MainActivity
import irawan.electroshock.doaku.R

@Composable
fun ShowAlertDialog(){
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Internet", color = Color.Black, fontSize = 18.sp)},
            text = { Text("Hidupkan Internet anda!", color = Color.Black, fontSize = 16.sp) },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        val activity = (context as? Activity)
                        activity?.finish()
                    }) {
                    Text("OK", color = Color.Black)
                }
            },
            backgroundColor = colorResource(id = R.color.white),
            contentColor = Color.White,
            modifier = Modifier.border(4.dp, Color.Red)
        )
    }
}