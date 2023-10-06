package com.example.healthgrind.firebase.database.challenge

import android.hardware.SensorManager
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.support.AutoResizingText
import com.example.healthgrind.support.FullScreenProgressIndicator
import com.example.healthgrind.support.HeartRateIndicator
import com.example.healthgrind.support.PlusAndFinishButtons
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.SensorStuff

@Composable
fun StrengthScreen(
    navController: NavHostController,
    sensorManager: SensorManager,
    viewModel: ChallengeViewModel,
    vibrator: Vibrator
) {
    val challenge = viewModel.currentChallengeState.value
    val heartRateState = viewModel.heartRate

    SensorStuff(sensorManager = sensorManager, viewModel = viewModel)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(bottom = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primaryVariant,
            text = "Challenge",
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            text = "${challenge.goal} ${challenge.title}",
        )
        AutoResizingText(text = "${challenge.current}/${challenge.goal}")
        HeartRateIndicator(heartRate = heartRateState.value)

        PlusAndFinishButtons(challenge, viewModel, vibrator)
    }

    FullScreenProgressIndicator(challenge.progress)

    if (challenge.finished) {
        navController.navigate(Screen.Reward.route)
    }

}
