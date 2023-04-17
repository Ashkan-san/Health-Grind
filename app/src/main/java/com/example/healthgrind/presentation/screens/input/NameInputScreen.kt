package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
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
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.register.SignUpViewModel
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.presentation.screens.input.TextInput

@Composable
fun NameInputScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel(),
    pref: SharedPreferences
) {
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
                text = "Name Input"
            )
        }

        item {
            TextInput(
                value = "Name",
                icon = R.drawable.player,
                onInputChange = {
                    viewModel.onNameChange(it)
                }
            )
        }

        item {
            Button(
                onClick = {
                    viewModel.onConfirmClick(navController, Screen.AgeInput.route, "name", pref)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Confirm Input"
                )
            }
        }
    }
}
