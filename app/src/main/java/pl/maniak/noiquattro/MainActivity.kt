package pl.maniak.noiquattro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.maniak.noiquattro.ui.screens.LoginScreen
import pl.maniak.noiquattro.ui.screens.StartScreen
import pl.maniak.noiquattro.ui.theme.NoiquattroTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoiquattroTheme {
                Surface {
                    val navController = rememberNavController()
                    NavigationComponent(navController = navController)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "start") {
        composable(route = "start") {
            StartScreen(onStartClick = { navController.navigate("login") })
        }
        composable(route = "login") {
            LoginScreen(
                onClickLogin = { str1, str2 -> navController.navigate("home") },
                onClickGoogle = {}
            )
        }
        composable(route = "home") {

        }
        composable(route = "item_detail") {

        }
        composable(route = "profile") {

        }
        composable(route = "shopping_bag") {

        }
        composable(route = "payment") {

        }
        composable(route = "order_history") {

        }
        composable(route = "map") {

        }
    }
}