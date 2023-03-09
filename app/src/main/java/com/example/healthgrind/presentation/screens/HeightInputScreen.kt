package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun HeightInputScreen(
    navController: NavHostController,
    pref: SharedPreferences,
    setPref: SharedPreferences
) {
    HeightPicker(
        onHeightConfirm = {
            pref.edit().putInt("height", it).apply()
            if (setPref.getBoolean("firststart", true)) {
                navController.navigate(Screen.Start.route)
                setPref.edit().putBoolean("firststart", false).apply()
            } else {
                navController.popBackStack()
            }
        }
    )

}

@Composable
fun HeightPicker(
    onHeightConfirm: (Int) -> Unit
) {
    val heightState = rememberPickerState(
        initialNumberOfOptions = 250,
        initiallySelectedOption = 170
    )

    val fr = remember { FocusRequester() }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val boxConstraints = this

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(16.dp))

            // Überschrift
            Text(
                text = "cm",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.button,
                maxLines = 1
            )

            // Spacing Kram
            val weightsToCenterVertically = 0.5f
            val width = 80.dp
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(weightsToCenterVertically)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset((boxConstraints.maxWidth - width) / 2 - 8.dp)
            ) {
                PickerImpl(
                    state = heightState,
                    onSelected = { },
                    // Funktion die als Input Int bekommt und String returned
                    text = { height: Int -> "%4d".format(height) },
                    width = width,
                    focusRequester = fr,
                    contentDescription = "%4d".format(heightState.selectedOption)
                )
            }
            // Spacing Kram
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(weightsToCenterVertically)
            )

            // Confirm Button
            Button(
                onClick = {
                    val confirmedHeight = heightState.selectedOption
                    onHeightConfirm(confirmedHeight)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Confirm",
                    modifier = Modifier
                        .size(24.dp)
                        .wrapContentSize(align = Alignment.Center)
                )
            }

            // Spacing Kram
            Spacer(Modifier.height(12.dp))
        }
    }
}