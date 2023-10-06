package com.example.healthgrind.presentation.screens.input

import android.content.SharedPreferences
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.register.AccountViewModel
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.TextInput

@Composable
fun NameInputScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel(),
    pref: SharedPreferences,
    vibrator: Vibrator
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            text = "Name"
        )
        TextInput(
            value = "Name",
            icon = R.drawable.player,
            onInputChange = {
                viewModel.onNameChange(it)
            }
        )
        Button(
            onClick = {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                viewModel.onConfirmClick(navController, Screen.AgeInput.route, "name", pref)
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = "Confirm Input"
            )
        }
    }
}
