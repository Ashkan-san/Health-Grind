package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun WeightInputScreen(
    navController: NavHostController,
    pref: SharedPreferences,
    setPref: SharedPreferences
) {
    WeightPicker(
        onWeightConfirm = {
            pref.edit().putInt("weight", it).apply()
            if (setPref.getBoolean("firststart", true)) {
                navController.navigate(Screen.HeightInput.route)
            } else {
                navController.popBackStack()
            }
        }
    )

}

@Composable
fun WeightPicker(
    onWeightConfirm: (Int) -> Unit
) {
    // STATES VON GEWICHT
    val weightState = rememberPickerState(
        initialNumberOfOptions = 300,
        initiallySelectedOption = 70
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
                text = "kg",
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
                    state = weightState,
                    onSelected = { },
                    // Funktion die als Input Int bekommt und String returned
                    text = { weight: Int -> "%4d".format(weight) },
                    width = width,
                    focusRequester = fr,
                    contentDescription = "%4d".format(weightState.selectedOption)
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
                    val confirmedWeight = weightState.selectedOption
                    onWeightConfirm(confirmedWeight)
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

@Composable
fun PickerImpl(
    state: PickerState,
    onSelected: () -> Unit,
    text: (Int) -> String,
    focusRequester: FocusRequester,
    contentDescription: String?,
    width: Dp
) {
    CustomPicker(
        readOnly = false,
        state = state,
        focusRequester = focusRequester,
        modifier = Modifier.size(width, 100.dp),
        contentDescription = contentDescription,
        onSelected = onSelected
    ) { option ->
        NumberPiece(
            selected = true,
            onSelected = onSelected,
            text = text(option),
            style = MaterialTheme.typography.display2
        )
    }

}