package pl.maniak.noiquattro.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.sampleMapData
import pl.maniak.noiquattro.ui.theme.Default50
import pl.maniak.noiquattro.ui.theme.Green800
import pl.maniak.noiquattro.ui.theme.Neutral900

@Composable
fun MapScreen(
    data: UiState.Map
) {
    var isDetailVisible by remember {
        mutableStateOf(true)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column {
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
                    text = "Your order",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            OrderMap()
        }

        AnimatedVisibility(
            visible = isDetailVisible,
            enter = slideIn { IntOffset(0, 100) } + fadeIn(),
            exit = slideOut { IntOffset(0, 100) } + fadeOut()
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                InfoCard(
                    name = data.name,
                    surname = data.surname,
                    sourceAddress = data.sourceAddress,
                    targetAddress = data.targetAddress
                )
            }
        }

        Box(contentAlignment = Alignment.BottomCenter) {
            OutlinedButton(
                colors = ButtonDefaults.buttonColors(Green800),
                shape = RoundedCornerShape(20),
                onClick = { isDetailVisible = !isDetailVisible }) {
                Text(text = if (isDetailVisible) "Hide details" else "Show details")
            }
        }
    }
}

@Composable
fun InfoCard(
    name: String = "",
    surname: String = "",
    sourceAddress: String = "",
    targetAddress: String = ""
) {
    val profileImage = ImageBitmap.imageResource(id = R.drawable.profile_image)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        color = Neutral900
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .size(60.dp, 60.dp)
                            .padding(end = 16.dp),
                        bitmap = profileImage, contentDescription = "Profile image"
                    )

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$name $surname",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = "123-456-789",
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )

                    }
                }

                Surface(shape = CircleShape, color = Default50) {
                    IconButton(modifier = Modifier.border(
                        1.dp, Color.LightGray,
                        shape = CircleShape
                    ),
                        onClick = { }) {
                        Icon(
                            modifier = Modifier.size(25.dp, 25.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_phone),
                            contentDescription = "Phone icon",
                            tint = Green800
                        )
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 30.dp)
                    .offset(y = -20.dp),
                shape = RoundedCornerShape(12),
                elevation = 1.dp
            ) {
                val placeImage = ImageVector.vectorResource(id = R.drawable.ic_place)
                val clockImage = ImageVector.vectorResource(id = R.drawable.ic_clock)

                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                    InfoCardRow(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                        image = placeImage,
                        address = sourceAddress
                    )

                    Image(
                        modifier = Modifier
                            .padding(start = 25.dp)
                            .size(30.dp, 70.dp),
                        bitmap = ImageBitmap.imageResource(id = R.drawable.ic_line),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )

                    InfoCardRow(
                        modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                        image = clockImage,
                        address = targetAddress
                    )
                }
            }
        }
    }
}

@Composable
fun InfoCardRow(modifier: Modifier = Modifier, image: ImageVector, address: String) {
    Row(
        modifier = modifier,
    ) {
        IconButton(
            modifier = Modifier.border(1.dp, Default50, shape = CircleShape)
                .background(color = Default50, shape = CircleShape),
            onClick = { }
        ) {
            Icon(
                modifier = Modifier.size(25.dp, 25.dp),
                tint = Green800,
                imageVector = image,
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.padding(start = 5.dp, top = 15.dp),
            text = address,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun OrderMap(modifier: Modifier = Modifier) {
    val startPlace = LatLng(40.71, -74.00)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(startPlace, 10f)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        elevation = 1.dp
    ) {
        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(startPlace),
                title = "Joe’s Pizza",
                snippet = "124 Fulton St, New York, NY 10038, USA"
            )
        }

        Box(contentAlignment = Alignment.TopCenter) {
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.buttonColors(Green800),
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = "15 min",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MapScreen(sampleMapData)
}