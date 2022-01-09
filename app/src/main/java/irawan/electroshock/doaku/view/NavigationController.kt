package irawan.electroshock.doaku.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.fragment.SearchFragment


@ExperimentalCoilApi
@Composable
fun NavigationController( context: Context, network : Boolean, data: List<DatabaseModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "DoaListFragment" ){
        composable("DoaListFragment"){
            DoaListFragment(context, network, navController, data)
        }
        composable("SearchFragment/{search}", arguments = listOf(navArgument("search"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("search")?.let { json ->
                val doa = Gson().fromJson(json, DatabaseModel::class.java)
                SearchFragment(context, network, navController, listOf(doa))
            }
        }
        composable("DoaDetailsFragment/{doa}", arguments = listOf(navArgument("doa"){
           type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("doa")?.let { json ->
                val doa = Gson().fromJson(json,DatabaseModel::class.java)
                DoaDetailsFragment(navController = navController, doa)
            }
        }
    }
}