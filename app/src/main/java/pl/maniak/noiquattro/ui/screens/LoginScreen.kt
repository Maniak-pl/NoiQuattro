package pl.maniak.noiquattro.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.maniak.noiquattro.R
import pl.maniak.noiquattro.ui.theme.Green800


@Composable
fun LoginScreen(
    onClickLogin: (String, String) -> Unit,
    onClickGoogle: () -> Unit,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val imageBitmap = ImageBitmap.imageResource(id = R.drawable.login_logo)

        Image(
            modifier = Modifier
                .sizeIn(minWidth = 60.dp, minHeight = 60.dp),
            bitmap = imageBitmap,
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            text = "Log in\nto your account"
        )

        EmailTextField(
            text = email,
            textLabel = "Email",
            onValueChange = { str -> email = str }
        )

        PasswordTextField(
            text = password,
            textLabel = "Password",
            onValueChange = { str -> password = str }
        )

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                fontWeight = FontWeight.SemiBold,
                color = Green800,
                text = "Forgot password?",
            )
        }

        OutlinedButton(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            onClick = { onClickLogin(email, password) },
            colors = ButtonDefaults.buttonColors(Green800)
        ) {

            Text(text = "Log in", color = Color.White)
        }

        OutlinedButton(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .height(48.dp)
                .fillMaxWidth(),
            onClick = { onClickGoogle() }) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    modifier = Modifier
                        .size(25.dp, 25.dp)
                        .padding(end = 5.dp),
                    bitmap = ImageBitmap.imageResource(id = R.drawable.ic_google),
                    contentDescription = null
                )
                Text(text = "Log in with Google", color = Color.Black)
            }
        }

        Box(
            modifier = Modifier.padding(top = 30.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Row {
                Text(text = "Don't have an account?")
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Sign up",
                    color = Green800,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun EmailTextField(
    text: String,
    textLabel: String,
    onValueChange: (String) -> Unit,
) {
    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            text = "E-mail address"
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = text,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(10),
            label = { Text(text = textLabel) },
            leadingIcon = {
                val emailIcon = Icons.Filled.Email
                Icon(imageVector = emailIcon, contentDescription = null)
            }
        )
    }
}

@Composable
fun PasswordTextField(
    text: String,
    textLabel: String,
    onValueChange: (String) -> Unit,
) {

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Column {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            text = "Password"
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = text,
            onValueChange = onValueChange,
            shape = RoundedCornerShape(10),
            label = { Text(text = textLabel) },
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val passwordIcon = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (passwordVisible) "Hide password"
                else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = passwordIcon, contentDescription = description)
                }
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onClickLogin = { _, _ -> }) {}
}