package pl.maniak.noiquattro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.sampleEmptyOrderHistoryData
import pl.maniak.noiquattro.data.samples.sampleOrderHistoryData
import pl.maniak.noiquattro.ui.theme.Green800

@Composable
fun OrderHistoryScreen(
    data: UiState.OrderHistory,
    onEmptyHistoryClick: () -> Unit = {}
) {
    when (data.orderList.isEmpty()) {
        true -> EmptyOrderHistory(onEmptyHistoryClick)
        false -> OrderHistory(data.orderList)
    }
}

@Composable
fun EmptyOrderHistory(onEmptyHistoryClick: () -> Unit) {

    val emptyHistoryImage = ImageBitmap.imageResource(id = R.drawable.empty_order_history)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(200.dp, 200.dp),
            bitmap = emptyHistoryImage,
            contentDescription = "Empty order history image"
        )
        Text(
            text = "You have no orders yet",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Please your first order, to see its details",
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Green800),
            onClick = onEmptyHistoryClick,
            shape = RoundedCornerShape(20)
        ) {
            Text(text = "Order now", color = Color.White)
        }
    }
}

@Composable
fun OrderHistory(orderList: List<Order>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column {
            OrderHistoryHeader(orderNumber = orderList.size)
            LazyColumn(contentPadding = PaddingValues(bottom = 100.dp)) {
                items(
                    items = orderList,
                    key = { order: Order -> order.item.id }
                ) { order ->
                    OrderHistoryItem(order)
                }
            }
        }
    }
}

@Composable
fun OrderHistoryItem(order: Order) {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(10.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(10)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 2.dp),
                text = "${order.item.orderState}, ${order.item.date}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${order.item.name}...",
                fontWeight = FontWeight.Light
            )
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomEnd) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.padding(end = 20.dp),
                        text = (order.item.price * order.count).toString(), fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Green800
                    )
                    val arrowRight = ImageVector.vectorResource(id = R.drawable.ic_right)
                    Icon(
                        imageVector = arrowRight,
                        contentDescription = "Arrow right",
                        tint = Green800
                    )
                }
            }
        }
    }
}

@Composable
fun OrderHistoryHeader(orderNumber: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = "Order history ($orderNumber)",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EmptyOrderHistoryScreenPreview() {
    OrderHistoryScreen(sampleEmptyOrderHistoryData)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OrderHistoryScreenPreview() {
    OrderHistoryScreen(sampleOrderHistoryData)
}