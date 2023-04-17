package com.example.healthgrind.firebase.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.dialog.DialogDefaults
import com.example.healthgrind.R
import com.example.healthgrind.presentation.screens.input.TextInput

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
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
                onInputChange = {
                    viewModel.onPasswordChange(it)
                }
            )
        }

        item {
            Button(
                onClick = {
                    var bool = viewModel.onSignUpClick(navController)

                    /*if (bool) {
                        ErrorDialog()
                    }*/
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

@Composable
fun ErrorDialog() {
    Dialog(
        showDialog = true,
        onDismissRequest = {
            //navController.navigate(Screen.Start.route)
        }
    ) {
        Confirmation(
            onTimeout = {
                //navController.navigate(Screen.Start.route)
            },
            durationMillis = DialogDefaults.ShortDurationMillis
        ) {

            Text(
                text = "Deine E-Mail oder Passwort sind falsch. Ein Passwort sollte min. 6 Zeichen lang sein.",
                textAlign = TextAlign.Center
            )

        }
    }
}