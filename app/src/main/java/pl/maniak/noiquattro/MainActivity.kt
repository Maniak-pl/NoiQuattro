package pl.maniak.noiquattro

import android.content.Context
import android.os.Bundle
import android.widget.Toast
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
import androidx.navigation.navOptions
import kotlinx.coroutines.launch
import pl.maniak.noiquattro.ui.screens.HomeScreen
import pl.maniak.noiquattro.ui.screens.LoginScreen
import pl.maniak.noiquattro.ui.screens.MapScreen
import pl.maniak.noiquattro.ui.screens.OrderHistoryScreen
import pl.maniak.noiquattro.ui.screens.PaymentScreen
import pl.maniak.noiquattro.ui.screens.ProductDetailScreen
import pl.maniak.noiquattro.ui.screens.ProfileScreen
import pl.maniak.noiquattro.ui.screens.ShoppingBagScreen
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
                    NavigationComponent(
                        navController = navController,
                        viewModel = vm,
                        onErrorAction = { message ->
                            showMessage(context = this, message = message)
                        }
                    )
                }
            }
        }
    }
}

private fun showMessage(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun NavigationComponent(
    navController: NavHostController,
    viewModel: MainViewModel,
    onErrorAction: (String) -> Unit,
) {
    val loginScope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {

        composable(route = "start") {
            StartScreen(onStartClick = { navController.navigate(route = "login") })
        }

        composable(route = "login") {
            LoginScreen(
                onClickLogin = { email, password ->
                    loginScope.launch {
                        viewModel.login(
                            email = email,
                            password = password,
                            onSuccessAction = {
                                navController.navigate(route = "home")
                            },
                            onErrorAction = {
                                onErrorAction(it)
                            }
                        )
                    }
                },
                onClickGoogle = {}
            )
        }

        composable(route = "home") {
            val home = viewModel.home

            home?.let { data ->
                HomeScreen(
                    data = data,
                    onItemClicked = { item ->
                        viewModel.updateItemState(item.id)
                        navController.navigate(route = "item_detail")
                    },
                    onProfileClicked = {
                        viewModel.updateProfileState()
                        navController.navigate(route = "profile")
                    },
                    onSearch = { str -> viewModel.filterData(str) },
                )
            }

        }

        composable(route = "item_detail") {
            val item = viewModel.item
            item?.let { data ->
                ProductDetailScreen(
                    data = data,
                    onItemAdd = { item -> viewModel.addItem(item) },
                    onGoToShoppingBag = { navController.navigate(route = "shopping_bag") },
                )
            }
        }

        composable(route = "profile") {
            val profile = viewModel.profileState
            profile?.let { data ->
                ProfileScreen(
                    data = data,
                    onHistoryClick = {
                        navController.navigate(route = "order_history")
                    },
                )
            }
        }

        composable(route = "shopping_bag") {
            val orderList = viewModel.shoppingBag.toList()
            val amount = viewModel.amount

            ShoppingBagScreen(
                shoppingList = orderList,
                roundedDouble = amount,
                onIncrementOrderNumber = { viewModel.increaseOrderNumber(it) },
                onDecrementOrderNumber = { viewModel.decreaseOrderNumber(it) },
                onPaymentClick = {
                    viewModel.preparePayment()
                    navController.navigate(route = "payment")
                },
            )
        }

        composable(route = "payment") {
            val payment = viewModel.payment
            payment?.let { it1 ->
                PaymentScreen(data = it1,
                    onClose = {
                        navController.navigate(
                            route = "shopping_bag",
                            navOptions = navOptions {
                                popUpTo(route = "shopping_bag") {
                                    inclusive = true
                                }
                            })
                    },
                    onPayClick = {
                        viewModel.updateMapState()
                        viewModel.clearShoppingBag()

                        navController.navigate(
                            route = "map",
                            navOptions = navOptions { popUpTo(route = "home") })
                    }
                )
            }

        }

        composable(route = "order_history") {
            val orderHistory = viewModel.orderHistory
            OrderHistoryScreen(
                data = orderHistory,
                onEmptyHistoryClick = {
                    navController.navigate(route = "home", navOptions = navOptions {
                        popUpTo(route = "order_history") {
                            inclusive = true
                        }
                    })
                })

        }

        composable(route = "map") {
            val mapData = viewModel.mapDetail
            mapData?.let { data -> MapScreen(data = data) }
        }
    }
}