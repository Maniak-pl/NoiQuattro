package pl.maniak.noiquattro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.maniak.noiquattro.ui.screens.HomeScreen
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
                    val vm = viewModel<MainViewModel>()
                    NavigationComponent(navController = navController, viewModel = vm)
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    val loginScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        composable(route = "start") {
            StartScreen(onStartClick = { navController.navigate("login") })
        }
        composable(route = "login") {
            LoginScreen(
                onClickLogin = { email, password ->
                    loginScope.launch {
                        viewModel.login(email, password)
                        navController.navigate("home")
                    }
                },
                onClickGoogle = {}
            )
        }
        composable(route = "home") {
            val home = viewModel.home

            if (home != null) {
                HomeScreen(
                    data = home,
                    onItemClicked = {},
                    onProfileClicked = {},
                    onSearch = {}
                )
            }
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