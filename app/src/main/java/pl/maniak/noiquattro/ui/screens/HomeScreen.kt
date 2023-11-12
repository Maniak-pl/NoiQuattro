package pl.maniak.noiquattro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import java.util.Collections.emptyList

@Composable
fun HomeScreen(
    // data
    onItemClicked: () -> Unit,
    onProfileClicked: () -> Unit,
    onSearch: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    var text by remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(start = 2.dp, end = 2.dp, bottom = 10.dp)
    ) {

        HomeHeader()
        WelcomeText()
        SearchField(text = text, onSearch = {
            text = it
            onSearch(it)
        })
        PromotionAds()
        OfferList()

    }
}


@Composable
fun HomeHeader(
    address: String = "",
    onProfileClicked: () -> Unit = {}
) {
    var isExpended by rememberSaveable {
        mutableStateOf(false)
    }

    val arrowIcon = when (isExpended) {
        true -> ImageVector.vectorResource(id = R.drawable.ic_arrow_down)
        false -> ImageVector.vectorResource(id = R.drawable.ic_arrow_up)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Column {
            Row(modifier = Modifier.clickable { isExpended = !isExpended }) {
                Text(
                    text = "Tour address",
                    color = Color.Gray
                )
                Icon(
                    imageVector = arrowIcon,
                    contentDescription = null
                )
            }

            if (isExpended) {
                Text(
                    text = address,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Image(
                modifier = Modifier
                    .clickable { onProfileClicked() }
                    .size(48.dp, 48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape),

                bitmap = ImageBitmap.imageResource(id = R.drawable.profile_image),
                contentDescription = null
            )
        }
    }
}

@Composable
fun WelcomeText(name: String = "") {
    Column(Modifier.padding(top = 20.dp)) {
        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = "Welcome $name,\nwhat would you like to eat?",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
        )
    }
}

@Composable
fun SearchField(
    text: String,
    onSearch: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 10.dp),
        value = text, onValueChange = { onSearch(it) },
        label = { Text(text = "Search for a dish") },
        leadingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                contentDescription = null
            )
        })
}

@Composable
fun PromotionAds() {
}

@Composable
fun OfferList() {
    TabHeaders()
}

@Composable
fun TabHeaders(
    selectedTab: String = "Pizza",
    headers: List<String> = emptyList(),
    onTabClick: (String) -> Unit = {},
) {

    LazyRow {
        items(items = headers) { header ->
            Text(modifier = Modifier
                .clickable { onTabClick(header) }
                .padding(horizontal = 16.dp, vertical = 10.dp),
                text = header,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = if (selectedTab == header) Color.Black else Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeHeaderPreview() {
    HomeHeader(address = "Rynek Główny 1, 31-042 Kraków")
}

@Preview(showBackground = true)
@Composable
fun WelcomeTextPreview() {
    WelcomeText(name = "John")
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onItemClicked = {},
        onProfileClicked = {},
        onSearch = {}
    )
}