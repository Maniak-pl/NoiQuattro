package pl.maniak.noiquattro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import pl.maniak.noiquattro.data.ItemDetail
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.UserData
import kotlin.math.max

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

    fun setItemDetail(id: Long) {
        val itemDetail = repository.getItemDetail(id)
        val isAlreadyAdded = shoppingBag.flatMap { listOf(it.item) }.contains(itemDetail)

        val newState = UiState.ItemDetailScreen(
            item = itemDetail ?: throw Exception("Item not found"),
            alreadyAdded = isAlreadyAdded,
        )

        item = newState
    }

    fun addItem(itemDetail: ItemDetail) {
        val order = Order(itemDetail, 1)

        shoppingBag = shoppingBag.plusElement(order)

        val newState = UiState.ItemDetailScreen(
            item = itemDetail,
            alreadyAdded = true
        )

        item = newState

        val totalAmount = shoppingBag.sumOf { it.item.price.toDouble() * it.count }
        val roundedDouble = String.format("%.2f", totalAmount).toDouble()

        amount = roundedDouble
    }

    fun increaseOrderNumber(itemDetail: ItemDetail) {
        val order = shoppingBag.find { it.item == itemDetail }
        val newVal = order?.count?.plus(1)!!

        order.count = newVal
        shoppingBag = shoppingBag.plus(order)

        val totalAmount = shoppingBag.sumOf { it.item.price.toDouble() * it.count }
        val roundedDouble = String.format("%.2f", totalAmount).toDouble()

        amount = roundedDouble
    }

    fun decreaseOrderNumber(itemDetail: ItemDetail) {
        val order = shoppingBag.find { it.item == itemDetail }
        val newValue = max(0, order?.count?.minus(1)!!)
        order.count = newValue

        if (newValue == 0) {
            shoppingBag = shoppingBag.minus(order)

            val newState = UiState.ItemDetailScreen(
                item = itemDetail,
                alreadyAdded = false
            )

            item = newState

        } else {
            shoppingBag = shoppingBag.plus(order)
        }

        val totalAmount = shoppingBag.sumOf { it.item.price.toDouble() * it.count }
        val roundedDouble = String.format("%.2f", totalAmount).toDouble()

        amount = roundedDouble
    }

    fun preparePayment() {
        val newState = UiState.Payment(
            userData = userData,
            orderList = shoppingBag.toList()
        )
        payment = newState
    }

    fun filterData(str: String) {
        if (str.isEmpty() || str.isBlank()) {
            home = home?.copy(products = repository.getAllItems())
            return
        }

        home?.products
            ?.filter { it.name.contains(str, ignoreCase = true) }
            ?.let { filteredList ->
                home = home?.copy(products = filteredList)
            }
    }

    fun updateMapState() {
        mapDetail = UiState.Map(
            name = userData.name,
            surname = userData.surname,
            sourceAddress = "124 Fulton St, New York",
            targetAddress = userData.address,
        )
    }

    fun updateProfileState() {
        profileState = UiState.Profile(
            name = userData.name,
            surname = userData.surname,
            email = userData.email
        )
    }
}