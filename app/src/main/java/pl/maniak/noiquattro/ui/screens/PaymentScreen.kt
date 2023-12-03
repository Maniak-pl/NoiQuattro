package pl.maniak.noiquattro.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.samplePayment
import pl.maniak.noiquattro.ui.theme.Green800

@Composable
fun PaymentScreen(
    data: UiState.Payment,
    onClose: () -> Unit = {},
    onPayClick: () -> Unit = {},
) {

    val roundedDouble by remember {
        val totalAmount = data.orderList.sumOf { it.item.price.toDouble() * it.count }
        Log.d("TAG", "PaymentScreen() item = ${data.orderList[2].item.price}, totalAmount = $totalAmount")
        val rounded = String.format("%.2f", totalAmount)
        mutableStateOf(rounded)
    }

    Column {
        PaymentHeader(onClose = onClose)
        PaymentCardDetail()
        PaymentAddress(address = data.userData.address)
        PaymentTotalCost(totalAmount = roundedDouble)
        PaymentButton(onPayClick = onPayClick)
    }
}

@Composable
fun PaymentHeader(onClose: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp),
            text = "Payment",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier.clickable { onClose() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(30.dp, 30.dp),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_close),
                contentDescription = null
            )
        }
    }
}

@Composable
fun PaymentCardDetail() {
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(horizontal = 16.dp),
        elevation = 3.dp,
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .size(35.dp, 35.dp),
                bitmap = ImageBitmap.imageResource(id = R.drawable.ic_visa_logo),
                contentDescription = null
            )
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "*** *** *** 1234")
                Text(text = "Payment Method")
            }
        }
    }
}

@Composable
fun PaymentAddress(address: String = "") {
    val placeIcon = ImageVector.vectorResource(id = R.drawable.ic_place)
    val editIcon = ImageVector.vectorResource(id = R.drawable.ic_edit)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Box() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    modifier = Modifier.padding(end = 16.dp),
                    imageVector = placeIcon,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Address",
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = address,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                }
            }
        }

        IconButton(modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(10)
            ),
            onClick = { }) {
            Icon(
                modifier = Modifier.size(30.dp, 30.dp),
                imageVector = editIcon,
                contentDescription = null
            )
        }
    }
}

@Composable
fun PaymentTotalCost(totalAmount: String = "") {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(
            text = "Total: ",
            color = Color.LightGray,
            fontWeight = FontWeight.Light,
            fontSize = 25.sp
        )
        Text(
            text = totalAmount,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
    }
}

@Composable
fun PaymentButton(onPayClick: () -> Unit = {}) {
    OutlinedButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        onClick = { onPayClick() },
        colors = ButtonDefaults.buttonColors(Green800),
    ) {
        Text(
            text = "Pay",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp, color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PaymentHeaderPreview() {
    PaymentHeader()
}

@Preview(showBackground = true)
@Composable
fun PaymentCardDetailPreview() {
    PaymentCardDetail()
}

@Preview(showBackground = true)
@Composable
fun PaymentAddressPreview() {
    PaymentAddress(address = "123 Main Street, Apt 13a, Anytown, USA")
}

@Preview(showBackground = true)
@Composable
fun PaymentTotalCostPreview() {
    PaymentTotalCost(totalAmount = "$385")
}

@Preview(showBackground = true)
@Composable
fun PaymentButtonPreview() {
    PaymentButton()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(data = samplePayment)
}