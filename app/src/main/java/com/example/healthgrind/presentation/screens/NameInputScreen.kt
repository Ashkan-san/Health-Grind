package com.example.healthgrind.presentation.screens

import android.app.RemoteInput
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.RemoteInputIntentHelper.Companion.putRemoteInputsExtra
import androidx.wear.input.wearableExtender
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun NameInputScreen(
    navController: NavHostController,
    pref: SharedPreferences,
    setPref: SharedPreferences
) {
    TextInput(
        onChange = {
            pref.edit().putString("name", it).apply()

            if (setPref.getBoolean("firststart", true)) {
                navController.navigate(Screen.AgeInput.route)
            } else {
                navController.popBackStack()
            }
        }
    )
}

@Composable
fun TextInput(
    onChange: (value: String) -> Unit,
) {
    val placeholder = ""
    var textForUserInput by remember { mutableStateOf("") }

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newValue: CharSequence? = results.getCharSequence(placeholder)

                textForUserInput = newValue.toString()

                onChange(textForUserInput)
            }
        }


    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        userScrollEnabled = false
    ) {
        item {
            Text(text = "Input your name:")
        }
        item {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()

            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(placeholder)
                    .setLabel(placeholder)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            putRemoteInputsExtra(intent, remoteInputs)
            Button(
                onClick = {
                    launcher.launch((intent))
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.keyboard),
                    contentDescription = "Input"
                )
            }
        }
    }
}