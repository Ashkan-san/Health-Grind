package com.example.healthgrind.presentation.screens.input

import android.content.SharedPreferences
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.rememberPickerState
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.register.AccountViewModel
import com.example.healthgrind.support.Screen

@Composable
fun HeightInputScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel(),
    pref: SharedPreferences,
    vibrator: Vibrator
) {
    HeightPicker(
        onHeightConfirm = {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            viewModel.onHeightChange(it)
            viewModel.onConfirmClick(navController, Screen.WeightInput.route, "height", pref)
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

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Größe"
            )

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