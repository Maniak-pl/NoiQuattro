package pl.maniak.noiquattro.data.samples

import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.ItemDetail
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.UserData

val sampleUserOne =
    UserData(1L, "John", "Smith", "123 Main Street, Apt 13a, Anytown, USA", email = "1@wp.pl", password = "1234")
val sampleUserTwo =
    UserData(2L, "Jacob", "Johnson", "456 Oak Street, Apt 13a, Anothercity, USA", email = "2@wp.pl", password = "1234")

val sampleItemDetailZero = ItemDetail(
    id = 0L,
    orderState = "Delivered",
    name = "Three cheeses",
    date = "June 5, 2022",
    ingredients = "Mozzarella, gran padano, gouda",
    price = 100.99f,
    image = R.drawable.pizza_two
)

val sampleItemDetailOne = ItemDetail(
    id = 1L,
    orderState = "Delivered",
    name = "Cheese and tomatoes",
    date = "June 5, 2022",
    ingredients = "Cheese and tomato, double pepperoni, Mexican x2",
    price = 91.23f,
    image = R.drawable.pizza_one
)

val sampleItemDetailTwo = ItemDetail(
    id = 2L,
    orderState = "Delivered",
    name = "Pepperoni with sausage",
    date = "June 5, 2022",
    ingredients = "Cheese and tomato, double pepperoni, Mexican x2",
    image = R.drawable.pizza_two,
    price = 35.99f
)

val sampleOrdersOne = buildList {
    for (i in 0..10) {
        val order = Order(item = sampleItemDetailOne.copy(id = i.toLong(), price = i.toFloat()), i)
        add(order)
    }
}.toList()

val sampleOrdersTwo = buildList {
    for (i in 0..10) {
        add(sampleItemDetailTwo.copy(id = i.toLong()))
    }
}.toList()

val sampleHeader = listOf("Pizza", "Salads", "Vegan", "Desserts")

val sampleHomeData = UiState.Home(
    products = sampleOrdersTwo,
    userData = sampleUserOne
)

val sampleOrderHistoryData =
    UiState.OrderHistory(orderList = sampleOrdersOne)

val sampleEmptyOrderHistoryData =
    UiState.OrderHistory(orderList = emptyList())


val sampleItemDetailScreen =
    UiState.ItemDetailScreen(item = sampleOrdersOne[0].item)

val sampleProfile = UiState.Profile(name = "John", surname = "Smith", email = "john.smith@w.pl")

val sampleShoppingBag = UiState.ShoppingBag(itemList = sampleOrdersOne)

val samplePayment = UiState.Payment(userData = sampleUserOne, orderList = sampleOrdersOne)

val sampleMapData = UiState.Map(
    name = "Jake",
    surname = "Smith",
    sourceAddress = "123 Main Street",
    targetAddress = "456 Hungerford Avenue"
)