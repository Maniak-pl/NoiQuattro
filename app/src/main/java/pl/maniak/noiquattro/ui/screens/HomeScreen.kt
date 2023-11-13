package pl.maniak.noiquattro.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.ui.theme.Green800
import pl.maniak.noiquattro.ui.theme.Neutral900
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
    Surface(
        color = Neutral900,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(10)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(start = 10.dp, top = 16.dp)) {
                Text(
                    text = "-20% discount",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "Vegetarian pizza", color = Color.White)
                IconButton(
                    modifier = Modifier
                        .padding(5.dp)
                        .border(
                            border = BorderStroke(1.dp, Color.LightGray),
                            shape = RoundedCornerShape(10)
                        ),
                    onClick = { }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Color.White
                    )

                }
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    modifier = Modifier
                        .size(150.dp, 150.dp),
                    bitmap = ImageBitmap.imageResource(id = R.drawable.pizza_three),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun OfferList(
    headers: List<String> = emptyList(),
    selectedCategoryTab: String = "",
    products: List<ItemDetail> = emptyList(),
    onTabClick: (String) -> Unit = {},
    onItemClicked: (ItemDetail) -> Unit = {},
) {
    Column() {
        TabHeaders(
            selectedTab = selectedCategoryTab,
            headers = headers,
            onTabClick = onTabClick
        )

        LazyRow {
            items(items = products) { item ->
                val bitmap = ImageBitmap.imageResource(id = item.image)
                OfferItem(
                    bitmap = bitmap,
                    item = item,
                    onItemClick = onItemClicked
                )
            }
        }
    }
}

@Composable
fun OfferItem(bitmap: ImageBitmap, item: ItemDetail, onItemClick: (ItemDetail) -> Unit = {}) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 5.dp)
            .clickable { onItemClick(item) },
        elevation = 10.dp,
        shape = RoundedCornerShape(10)
    ) {
        Column {
            Row {
                Image(
                    modifier = Modifier
                        .size(width = 150.dp, height = 150.dp)
                        .offset(x = 25.dp),
                    bitmap = bitmap,
                    contentDescription = null,
                    alignment = Alignment.TopCenter
                )

                Surface(elevation = 16.dp, shape = RoundedCornerShape(10)) {
                    Box(contentAlignment = Alignment.TopCenter) {
                        Image(
                            modifier = Modifier.size(50.dp, 50.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            alignment = Alignment.BottomEnd,
                            colorFilter = ColorFilter.tint(Green800)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = item.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 16.dp),
                text = item.price.toString()
            )
        }
    }
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

@Preview(showBackground = true)
@Composable
fun PromotionAdsPreview() {
    PromotionAds()
}

@Preview(showBackground = true)
@Composable
fun OfferItemPreview() {
    OfferItem(
        bitmap = ImageBitmap.imageResource(id = R.drawable.pizza_one),
        item = ItemDetail(
            id = 1L,
            orderState = "Delivered",
            name = "Cheese and tomatoes",
            date = "5 June 2022",
            ingredients = "Cheese and tomatoes, double pepperoni, mexican x2",
            price = 91.23f,
            image = R.drawable.pizza_one
        )
    )
}