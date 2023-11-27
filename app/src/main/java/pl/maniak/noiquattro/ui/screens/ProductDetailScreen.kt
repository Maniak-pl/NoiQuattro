package pl.maniak.noiquattro.ui.screens


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import pl.maniak.noiquattro.data.ItemDetail
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.sampleItemDetailScreen
import pl.maniak.noiquattro.ui.theme.Default50
import pl.maniak.noiquattro.ui.theme.Green800
import pl.maniak.noiquattro.ui.theme.Neutral100
import pl.maniak.noiquattro.ui.theme.Neutral900

@Composable
fun ProductDetailScreen(
    data: UiState.ItemDetailScreen,
    onItemAdd: (ItemDetail) -> Unit = {},
    onGoToShoppingBag: () -> Unit = {}
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ProductHeader()
        ProductImage(image = data.item.image)
        ProductDetail(item = data.item, alreadyAdded = data.alreadyAdded)
    }
}

@Composable
fun ProductHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
        Icon(
            modifier = Modifier
                .size(35.dp, 35.dp)
                .clip(CircleShape)
                .background(color = Neutral100),
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_favourite_border),
            contentDescription = null
        )
    }
}

@Composable
fun ProductImage(image: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .size(350.dp, 350.dp)
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = image), contentDescription = null,
            alignment = Alignment.Center
        )
    }
}


@Composable
fun ProductDetail(
    item: ItemDetail,
    alreadyAdded: Boolean = false,
    onItemAdd: (ItemDetail) -> Unit = {},
    onGoToShoppingBag: () -> Unit = {}
) {
    var isIngredientsExpanded by rememberSaveable { mutableStateOf(false) }
    var isCaloriesTableExpanded by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp),
        shape = RoundedCornerShape(10)
    ) {
        Column {
            LazyRow {
                items(items = item.hashTags) { tag ->
                    ProductHashTag(name = tag)
                }
            }

            Row(modifier = Modifier.padding(top = 20.dp)) {
                Text(
                    modifier = Modifier.weight(1f),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    text = item.name
                )

                Text(
                    modifier = Modifier.weight(1f),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold,
                    text = item.price.toString(),
                    textAlign = TextAlign.Center
                )
            }

            Column {
                Row(modifier = Modifier
                    .clickable { isIngredientsExpanded = !isIngredientsExpanded }
                    .padding(top = 45.dp)) {

                    val ingredientsArrow = when (isIngredientsExpanded) {
                        true -> R.drawable.ic_arrow_up
                        false -> R.drawable.ic_arrow_down
                    }

                    Text(
                        text = "Ingredients",
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = ingredientsArrow),
                        contentDescription = null
                    )
                }

                if (isIngredientsExpanded) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = item.ingredients,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Column {
                Row(modifier = Modifier
                    .clickable { isCaloriesTableExpanded = !isCaloriesTableExpanded }
                    .padding(top = 25.dp)) {

                    val caloriesArrow = when (isCaloriesTableExpanded) {
                        true -> R.drawable.ic_arrow_up
                        false -> R.drawable.ic_arrow_down
                    }

                    Text(
                        text = "Calories",
                        color = Color.Gray
                    )
                    Icon(
                        imageVector = ImageVector.vectorResource(id = caloriesArrow),
                        contentDescription = null
                    )

                }
                if (isCaloriesTableExpanded) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp),
                        text = item.calories,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

            ShoppingBagButton(
                alreadyAdded = alreadyAdded,
                onClick = { onItemAdd(item) },
                onGoToShoppingBag = onGoToShoppingBag
            )
        }
    }
}

@Composable
fun ShoppingBagButton(
    alreadyAdded: Boolean,
    onClick: () -> Unit,
    onGoToShoppingBag: () -> Unit = {}
) {
    val defaultModifier = Modifier
        .padding(vertical = 16.dp)
        .height(48.dp)
        .fillMaxWidth()

    when (alreadyAdded) {
        true -> {
            OutlinedButton(
                modifier = defaultModifier,
                onClick = onGoToShoppingBag,
                colors = ButtonDefaults.outlinedButtonColors(Neutral900)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_already_added),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(start = 10.dp),
                            text = "Already added",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
        else -> {
            OutlinedButton(
                modifier = defaultModifier,
                onClick = onClick,
                colors = ButtonDefaults.outlinedButtonColors(Green800)
            ) {
                Text(
                    text = "Add to shopping bag",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun ProductHashTag(name: String) {
    Surface(modifier = Modifier.padding(5.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(10),
        color = Default50
    ) {
        Text(
            text = name,
            modifier = Modifier.padding(7.dp),
            color = Color.Black
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(sampleItemDetailScreen)
}