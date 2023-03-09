package com.example.healthgrind.presentation.screens

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import com.example.healthgrind.R
import com.example.healthgrind.firebase.SignUpViewModel

@Composable
fun SignUpScreen(
    //openAndPopUp: (String, String) -> Unit,
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    MailInput(
        value = "E-Mail",
        onMailChange = {
            viewModel.onEmailChange(it)
        }
    )

    // TODO weiter
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        //userScrollEnabled = false
    ) {
        Text(text = uiState.email)
        Text(text = uiState.password)
        //EmailField(uiState.email, viewModel::onEmailChange)
        //PasswordField(uiState.password, viewModel::onPasswordChange)

        Button(
            onClick = {
                viewModel.onSignUpClick(navController)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.check),
                contentDescription = "Register"
            )
        }
    }

}

@Composable
fun MailInput(
    value: String?,
    onMailChange: (value: String) -> Unit
) {
    val placeholder = ""
    var textForUserInput by remember { mutableStateOf("") }

    // TEXT INPUT LAUNCHER ZEUG
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newValue: CharSequence? = results.getCharSequence(placeholder)

                // DER GEWOLLTE STRING
                textForUserInput = newValue.toString()

                onMailChange(textForUserInput)
            }
        }

    // COLUMN
    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = false
    ) {
        item {
            Text(text = "E-Mail")
        }
        item {
            Chip(
                label = { Text(if (value == null || value.isEmpty()) placeholder else value) },
                onClick = {
                    val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent();
                    val remoteInputs: List<RemoteInput> = listOf(
                        RemoteInput.Builder(placeholder)
                            .setLabel(placeholder)
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
        /*item {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()

            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(placeholder)
                    .setLabel(placeholder)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            // MAIL BUTTON
            Button(
                onClick = {
                    launcher.launch((intent))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.mail),
                    contentDescription = "Email Adresse"
                )
            }
        }*/
    }
}