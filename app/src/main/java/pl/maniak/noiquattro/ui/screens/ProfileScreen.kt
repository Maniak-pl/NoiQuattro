package pl.maniak.noiquattro.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.sampleProfile
import pl.maniak.noiquattro.ui.theme.Green800

@Composable
fun ProfileScreen(
    data: UiState.Profile,
    onHistoryClick: () -> Unit = {},
    onProfileDataClick: () -> Unit = {},
    onAddressClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
) {
    Column {
        ProfileHeader()
        ProfileInfo(name = data.name, surname = data.surname, email = data.email)
        ProfileMenu(onHistoryClick, onProfileDataClick, onAddressClick, onPaymentClick)
        ProfileHelp()
    }
}

@Composable
fun ProfileHeader() {
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
            text = "Profile",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp
        )
    }
}

@Composable
fun ProfileInfo(name: String = "", surname: String = "", email: String = "") {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "$name $surname",
            fontStyle = FontStyle.Normal,
            fontSize = 40.sp,
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
            text = email,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic,
        )
    }
}


@Composable
fun ProfileMenu(
    onHistoryClick: () -> Unit = {},
    onProfileDataClick: () -> Unit = {},
    onAddressClick: () -> Unit = {},
    onPaymentClick: () -> Unit = {},
) {
    Column {
        ProfileButton(
            iconId = R.drawable.ic_history,
            buttonText = "Order history",
            onClick = onHistoryClick
        )
        ProfileButton(
            iconId = R.drawable.ic_profile,
            buttonText = "Profile data",
            onClick = onProfileDataClick
        )
        ProfileButton(
            iconId = R.drawable.ic_place,
            buttonText = "Address",
            onClick = onAddressClick
        )
        ProfileButton(
            iconId = R.drawable.ic_payment,
            buttonText = "Payment",
            onClick = onPaymentClick
        )
    }
}

@Composable
fun ProfileHelp() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        ProfileButton(
            iconId = R.drawable.ic_help,
            buttonText = "Help",
            onClick = {}
        )
        ProfileButton(
            buttonText = "Log out",
            onClick = {}
        )
    }
}


@Composable
fun ProfileButton(
    @DrawableRes iconId: Int? = null,
    buttonText: String,
    onClick: () -> Unit = {},
) {
    val arrowRight = ImageVector.vectorResource(id = R.drawable.ic_right)

    OutlinedButton(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 3.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(Color.White),
        onClick = onClick,
        shape = RoundedCornerShape(20)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconId != null) {
                Image(
                    modifier = Modifier
                        .size(25.dp, 25.dp)
                        .padding(end = 10.dp),
                    imageVector = ImageVector.vectorResource(id = iconId),
                    contentDescription = "Icon"
                )
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = buttonText,
                textAlign = TextAlign.Start
            )

            Image(
                imageVector = arrowRight,
                contentDescription = "Arrow right",
                colorFilter = ColorFilter.tint(Green800)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileButtonPreview() {
    ProfileButton(iconId = R.drawable.ic_history, buttonText = "Order history")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(data = sampleProfile)
}