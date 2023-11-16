package pl.maniak.noiquattro.data


sealed class UiState {
    data class Home(
        val products: List<ItemDetail>,
        val userData: UserData,
    ) : UiState()

    data class Profile(
        val name: String,
        val surname: String,
        val email: String
    ) : UiState()

    data class ShoppingBag(
        val itemList: List<Order>
    ) : UiState()

    data class OrderHistory(
        val orderList: List<Order>
    ) : UiState()

    data class ItemDetailScreen(
        val item: ItemDetail,
        val alreadyAdded: Boolean = false
    ) : UiState()

    data class Payment(
        val userData: UserData,
        val orderList: List<Order>
    ) : UiState()

    data class Map(
        var name: String,
        val surname: String,
        val sourceAddress: String,
        val targetAddress: String,
    ) : UiState()
}