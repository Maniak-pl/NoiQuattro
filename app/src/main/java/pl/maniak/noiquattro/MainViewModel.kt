package pl.maniak.noiquattro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.UserData

class MainViewModel : ViewModel() {

    private val repository = RestaurantRepository()

    private lateinit var userData: UserData

    var shoppingBag by mutableStateOf(setOf<Order>())
        private set

    var amount by mutableStateOf(0.0)
        private set

    var item by mutableStateOf<UiState.ItemDetailScreen?>(null)
        private set

    var payment by mutableStateOf<UiState.Payment?>(null)
        private set

    var mapDetail by mutableStateOf<UiState.Map?>(null)
        private set

    var home by mutableStateOf<UiState.Home?>(null)
        private set

    var profileState by mutableStateOf<UiState.Profile?>(null)
        private set

    var orderHistory by mutableStateOf(UiState.OrderHistory(emptyList()))
        private set

    suspend fun login(email: String, password: String) {
        userData = repository.login(email, password) ?: throw Exception("User not found")
        updateHomeState()
    }

    private fun updateHomeState() {
        home = UiState.Home(products = repository.getAllItems(), userData = userData)
    }
}