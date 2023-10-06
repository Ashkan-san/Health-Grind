package com.example.healthgrind.firebase.auth.register

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.support.TextInput

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel(),
    vibrator: Vibrator
) {
    val signupError = remember { mutableStateOf(false) }
    val buttonClicked = remember { mutableStateOf(false) }
    val state = rememberScalingLazyListState(initialCenterItemIndex = 2)

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        state = state
    )
    {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Registrierung"
            )
        }
        item {
            if (signupError.value) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    text = "Deine E-Mail/Passwort ist falsch. " +
                            "Passwort Bedingungen: Min. 4 Zeichen lang, min. eine Zahl, min. ein Großbuchstaben."
                )
            } else if (!signupError.value && buttonClicked.value) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.secondaryVariant,
                    text = "Bitte warten..."
                )
            }
        }

        item {
            TextInput(
                value = "E-Mail",
                icon = R.drawable.mail,
                onInputChange = {
                    viewModel.onEmailChange(it)
                }
            )
        }

        item {
            TextInput(
                value = "Passwort",
                icon = R.drawable.lock,
                isPassword = true,
                onInputChange = {
                    viewModel.onPasswordChange(it)
                }
            )
        }

        item {
            Button(
                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    val bool = viewModel.onSignUpClick(navController)
                    if (!bool) signupError.value = true
                    buttonClicked.value = true
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Register"
                )
            }
        }
    }
}