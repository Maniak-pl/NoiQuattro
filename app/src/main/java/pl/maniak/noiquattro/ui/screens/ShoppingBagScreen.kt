package pl.maniak.noiquattro.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.ItemDetail
import pl.maniak.noiquattro.data.Order
import pl.maniak.noiquattro.data.samples.sampleShoppingBag
import pl.maniak.noiquattro.ui.theme.Green800

@Composable
fun ShoppingBagScreen(
    shoppingList: List<Order>,
    roundedDouble: Double = 0.0,
    onIncrementOrderNumber: (ItemDetail) -> Unit = {},
    onDecrementOrderNumber: (ItemDetail) -> Unit = {},
    onPaymentClick: () -> Unit = {}
) {
    Column {
        Box(modifier = Modifier.padding(start = 16.dp, top = 25.dp)) {
            Text(text = "Shopping basket", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        }

        ShoppingBagList(
            modifier = Modifier.weight(1f),
            shoppingList,
            onIncrementOrderNumber,
            onDecrementOrderNumber
        )

        SumUP(
            roundedDouble = roundedDouble,
            onPaymentClick = onPaymentClick
        )
    }
}

@Composable
fun ShoppingBagList(
    modifier: Modifier = Modifier,
    shoppingList: List<Order>,
    onIncrementOrderNumber: (ItemDetail) -> Unit,
    onDecrementOrderNumber: (ItemDetail) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 50.dp)
    ) {
        items(items = shoppingList) { order ->
            ShoppingBagItem(
                order = order,
                onIncrementOrderNumber,
                onDecrementOrderNumber
            )
        }
    }
}

@Composable
fun ShoppingBagItem(
    order: Order,
    onIncrementOrderNumber: (ItemDetail) -> Unit = {},
    onDecrementOrderNumber: (ItemDetail) -> Unit = {},
) {
    val pizzaImage = ImageBitmap.imageResource(id = order.item.image)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp),
        elevation = 5.dp,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier
                        .size(125.dp, 125.dp),
                    bitmap = pizzaImage,
                    contentDescription = null
                )

                Column {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(bottom = 10.dp),
                        text = order.item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val addIcon = ImageVector.vectorResource(id = R.drawable.ic_add)
                        val minusIcon = ImageVector.vectorResource(id = R.drawable.ic_minus)
                        IconButton(
                            modifier = Modifier
                                .border(
                                    BorderStroke(1.dp, color = Color.LightGray),
                                    shape = RoundedCornerShape(20)
                                ),
                            onClick = {
                                onDecrementOrderNumber(order.item)
                            }
                        ) {
                            Icon(imageVector = minusIcon, contentDescription = null, tint = Green800)
                        }

                        Text(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            fontWeight = FontWeight.Bold,
                            text = order.count.toString(),
                            fontSize = 18.sp
                        )

                        IconButton(
                            modifier = Modifier
                                .border(
                                    BorderStroke(1.dp, color = Color.LightGray),
                                    shape = RoundedCornerShape(20)
                                ),
                            onClick = {
                                onIncrementOrderNumber(order.item)
                            }
                        ) {
                            Icon(imageVector = addIcon, contentDescription = null, tint = Green800)
                        }
                    }
                }

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = order.item.price.toString(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SumUP(
    roundedDouble: Double,
    onPaymentClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SumUpRowText(
            textSize = 18.sp,
            fontWeight = FontWeight.Light,
            leftText = "Total",
            rightText = roundedDouble.toString()
        )

        SumUpRowText(
            textSize = 18.sp,
            fontWeight = FontWeight.Light,
            leftText = "Delivery",
            rightText = "10.0"
        )

        SumUpRowText(
            textSize = 25.sp,
            fontWeight = FontWeight.Bold,
            leftText = "Total cost",
            rightText = (roundedDouble + 10).toString()
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            onClick = onPaymentClick,
            colors = ButtonDefaults.buttonColors(Green800)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Payment",
                color = Color.White
            )
        }
    }
}

@Composable
fun SumUpRowText(
    textSize: TextUnit,
    fontWeight: FontWeight,
    leftText: String,
    rightText: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = leftText,
            fontSize = textSize,
            fontWeight = fontWeight
        )
        Text(
            modifier = Modifier.padding(end = 16.dp),
            text = rightText,
            fontSize = textSize,
            fontWeight = fontWeight,
            textAlign = TextAlign.End
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingBagItemPreview() {
    ShoppingBagItem(order = sampleShoppingBag.itemList[0])
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ShoppingBagScreenPreview() {
    ShoppingBagScreen(shoppingList = sampleShoppingBag.itemList)
}