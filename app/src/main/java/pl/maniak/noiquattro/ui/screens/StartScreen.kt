package pl.maniak.noiquattro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Bottom
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.ui.theme.Green800


@Composable
fun StartScreen(onStartClick: () -> Unit = {}) {
    val image = ImageBitmap.imageResource(
        id = R.drawable.start_screen_background
    )

    Image(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        bitmap = image,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        alpha = 0.5f
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Bottom
    ) {
        Text(
            modifier = Modifier.align(CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            text = "Noi Quattro"
        )
        Text(
            modifier = Modifier.padding(bottom = 100.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            color = Color.White,
            text = "Italian Pizza"
        )

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 100.dp)
                .height(60.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(Green800),
            onClick = { onStartClick() }) {
            val iconArrow = ImageVector.vectorResource(id = R.drawable.ic_arrow_right)

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Log in",
                    color = Color.White,
                    fontSize = 18.sp,
                )
                Icon(
                    modifier = Modifier.padding(start = 10.dp),
                    imageVector = iconArrow,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenPreview() {
    StartScreen()
}