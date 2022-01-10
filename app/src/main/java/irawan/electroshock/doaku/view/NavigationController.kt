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
import irawan.electroshock.doaku.view.fragment.RemoteSearchFragment
import irawan.electroshock.doaku.view_model.DataViewModel


@ExperimentalCoilApi
@ExperimentalComposeUiApi
@Composable
fun NavigationController( context: Context, network : Boolean, dataViewModel: DataViewModel, data: List<DatabaseModel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "DoaListFragment" ){
        composable("DoaListFragment"){
            DoaListFragment(context, network, navController, dataViewModel, data)
        }
        composable("RemoteSearchFragment/{remoteSearch}", arguments = listOf(navArgument("remoteSearch"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("remoteSearch").let { json ->
                val searchRemote : DatabaseModel = Gson().fromJson(json, DatabaseModel::class.java )
                RemoteSearchFragment(context, network, navController, dataViewModel, searchRemote)
            }
        }
        composable("SearchFragment/{searchDatabase}", arguments = listOf(navArgument("searchDatabase"){
            type = NavType.StringType
        })){ backStackEntry ->
            backStackEntry.arguments?.getString("searchDatabase").let{ json ->
                val search : List<DatabaseModel> = GsonBuilder().create().fromJson(json, Array<DatabaseModel>::class.java).toList()
                DoaListFragment(context, network, navController,dataViewModel, search)
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