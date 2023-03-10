package com.example.healthgrind.presentation.screens

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.dialog.DialogDefaults
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    // TODO weiter
    val uiState by viewModel.uiState

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
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

@Composable
fun TextInput(
    value: String?,
    icon: Int,
    onInputChange: (value: String) -> Unit
) {
    var textForUserInput by remember { mutableStateOf("") }

    // TEXT INPUT LAUNCHER ZEUG
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newValue: CharSequence? = results.getCharSequence(value)

                // DER GEWOLLTE STRING
                textForUserInput = newValue.toString()

                onInputChange(textForUserInput)
            }
        }

    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onPrimary,
                text = value!!
            )
        },
        secondaryLabel = {
            Text(
                text = textForUserInput,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        },
        // Tastatur öffnen
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent();
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(value!!)
                    .setLabel(value)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            launcher.launch(intent)
        }
    )
}