package pl.maniak.noiquattro.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
        ShoppingHeader()

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
fun ShoppingHeader() {
    val arrowLeft = ImageVector.vectorResource(id = R.drawable.ic_arrow_left)

    Row(
        modifier = Modifier.padding(vertical = 20.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = arrowLeft,
            contentDescription = "Arrow left"
        )
        Text(
            text = "Shopping basket",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
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

                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .offset(y = 29.dp)
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "$" + order.item.price.toString(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }

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
            modifier = Modifier.padding(top = 5.dp),
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
            modifier = Modifier.padding(top = 5.dp),
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
            colors = ButtonDefaults.buttonColors(Green800),
            shape = RoundedCornerShape(20)
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Payment",
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun SumUpRowText(
    modifier: Modifier = Modifier,
    textSize: TextUnit,
    fontWeight: FontWeight,
    leftText: String,
    rightText: String,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = modifier.padding(start = 16.dp),
            text = leftText,
            fontSize = textSize,
            fontWeight = fontWeight,
            color = Color.Gray
        )
        Text(
            modifier = modifier.padding(end = 16.dp),
            text = "$" + rightText,
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