package pl.maniak.noiquattro.data.samples

import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.ItemDetail
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.UserData

val sampleUserOne =
    UserData(1L, "John", "Smith", "1115 Madison Ave, New York, USA", email = "john@wp.com", password = "1234")
val sampleUserTwo =
    UserData(2L, "Jacob", "Johnson", "88 Central Park West, New York, USA", email = "jacob@wp.com", password = "1234")

val sampleItemDetailZero = ItemDetail(
    id = 0L,
    orderState = "Delivered",
    name = "Three cheeses",
    date = "June 5, 2022",
    ingredients = "Mozzarella, gran padano, gouda",
    price = 25.25f,
    image = R.drawable.pizza_two
)

val sampleItemDetailOne = ItemDetail(
    id = 1L,
    orderState = "Delivered",
    name = "Cheese and tomatoes",
    date = "June 5, 2022",
    ingredients = "Cheese and tomato, double pepperoni, Mexican x2",
    price = 22.8f,
    image = R.drawable.pizza_one
)

val sampleItemDetailTwo = ItemDetail(
    id = 2L,
    orderState = "Delivered",
    name = "Pepperoni with sausage",
    date = "June 5, 2022",
    ingredients = "Cheese and tomato, double pepperoni, Mexican x2",
    image = R.drawable.pizza_two,
    price = 8.90f
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
    name = "John",
    surname = "Smith",
    sourceAddress = "124 Fulton St, New York",
    targetAddress = "1115 Madison Ave, New York"
)