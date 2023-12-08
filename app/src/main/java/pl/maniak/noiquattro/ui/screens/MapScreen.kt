package pl.maniak.noiquattro.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.LatLng
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
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
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import pl.maniak.noiquattro.data.UiState
import pl.maniak.noiquattro.data.samples.sampleMapData
import pl.maniak.noiquattro.R
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
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Your order",
                textAlign = TextAlign.Start,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            OrderMap()
        }

        AnimatedVisibility(visible = isDetailVisible) {
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
        }
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MapScreen(sampleMapData)
}