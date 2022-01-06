package irawan.electroshock.doaku.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import irawan.electroshock.doaku.model.DatabaseModel
import irawan.electroshock.doaku.view.fragment.SearchButton


@Composable
fun NavigationController(data: List<DatabaseModel>, network : Boolean) {
    val navController = rememberNavController()

    SearchButton(network)
    NavHost(navController = navController, startDestination = "DoaListFragment" ){
        composable("DoaListFragment"){
            DoaListFragment(navController = navController, data)
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