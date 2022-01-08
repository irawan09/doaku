package irawan.electroshock.doaku.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.fragment.SearchFragment


@ExperimentalComposeUiApi
@Composable
fun NavigationController( context: Context, network : Boolean, data: List<DatabaseModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "DoaListFragment" ){
        composable("DoaListFragment"){
            DoaListFragment(context, network, navController, data)
        }
        composable("SearchFragment"){
            SearchFragment(context, network, navController)
        }
        composable("DoaDetailsFragment/{doa}", arguments = listOf(navArgument("doa"){
           type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry?.arguments?.getString("doa")?.let { json ->
                val doa = Gson().fromJson(json,DatabaseModel::class.java)
                DoaDetailsFragment(navController = navController, doa)
            }
        }
    }
}