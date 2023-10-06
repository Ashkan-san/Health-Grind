package com.example.healthgrind.presentation.screens.input

import android.content.SharedPreferences
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.RadioButton
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.register.AccountViewModel
import com.example.healthgrind.support.ProfileType
import com.example.healthgrind.support.Screen

@Composable
fun ProfileInputScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel(),
    pref: SharedPreferences,
    vibrator: Vibrator
) {
    val signupError = remember { mutableStateOf(false) }
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)
    val radioOptions = ProfileType.getList()
    val radioBools = listOf(
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
    )

    var profile = viewModel.userState.value.profile

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Profil"
            )
        }
        item {
            if (signupError.value) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.error,
                    text = "Bitte wähle dein Profil aus."
                )
            }
        }

        itemsIndexed(radioOptions) { index, option ->
            ToggleChip(
                modifier = Modifier.size(180.dp, 50.dp),
                label = {
                    Text(
                        text = option,
                        textAlign = TextAlign.Center
                    )
                },
                checked = radioBools[index].value,
                onCheckedChange = {
                    radioBools.forEach { it.value = false }
                    radioBools[index].value = true

                    profile = option
                },
                toggleControl = {
                    RadioButton(
                        selected = radioBools[index].value,
                        modifier = Modifier.semantics {
                            this.contentDescription = if (radioBools[index].value) "On" else "Off"
                        }
                    )
                }
            )
        }
        item {
            // Confirm Button
            Button(
                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))

                    signupError.value = false
                    if (profile == "") {
                        signupError.value = true
                    }

                    if (!signupError.value) {
                        viewModel.onProfileChange(profile)
                        viewModel.onConfirmClick(
                            navController,
                            Screen.NameInput.route,
                            "profile",
                            pref
                        )
                    }
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_right),
                    contentDescription = "Confirm",
                    modifier = Modifier
                        .size(24.dp)
                        .wrapContentSize(align = Alignment.Center)
                )

            }
        }
    }
}