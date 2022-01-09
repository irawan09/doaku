package irawan.electroshock.doaku.view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import irawan.electroshock.doaku.model.DatabaseModel


@ExperimentalCoilApi
<<<<<<< HEAD
=======
@ExperimentalComposeUiApi
>>>>>>> 67488e193448f0c8eeff5327579c6770a30d9356
@Composable
fun NavigationController( context: Context, network : Boolean, data: List<DatabaseModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "DoaListFragment" ){
        composable("DoaListFragment"){
            DoaListFragment(context, network, navController, data)
        }
<<<<<<< HEAD
        composable("SearchFragment/{search}", arguments = listOf(navArgument("search"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("search")?.let { json ->
                val doa = Gson().fromJson(json, DatabaseModel::class.java)
                SearchFragment(context, network, navController, listOf(doa))
=======
        composable("SearchFragment/{searchDatabase}", arguments = listOf(navArgument("searchDatabase"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("searchDatabase").let{ json ->
                val search : List<DatabaseModel> = GsonBuilder().create().fromJson(json, Array<DatabaseModel>::class.java).toList()
                DoaListFragment(context, network, navController, search)
>>>>>>> 67488e193448f0c8eeff5327579c6770a30d9356
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