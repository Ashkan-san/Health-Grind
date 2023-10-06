package com.example.healthgrind.firebase.database.challenge

import android.hardware.SensorManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.healthgrind.support.CountDownButton
import com.example.healthgrind.support.FullScreenProgressIndicator
import com.example.healthgrind.support.HeartRateIndicator
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.SensorStuff
import com.example.healthgrind.support.StepsInMeterIndicator
import com.example.healthgrind.support.myFormatTime

@Composable
fun RunScreen(
    viewModel: ChallengeViewModel,
    sensorManager: SensorManager,
    navController: NavHostController,
    vibrator: Vibrator
) {
    val challenge = viewModel.currentChallengeState.value
    val heartRateState = viewModel.heartRate
    val stepsInMeterState = viewModel.stepsInMeter

    SensorStuff(sensorManager = sensorManager, viewModel = viewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .padding(bottom = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
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
            text = "${myFormatTime(challenge.goal.toLong())} ${challenge.title}",
        )
        AutoResizingText(text = myFormatTime(challenge.current.toLong()))
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            HeartRateIndicator(heartRate = heartRateState.value)
            StepsInMeterIndicator(stepsInMeter = stepsInMeterState.value)
        }
        CountDownButton(isPlaying = viewModel.isPlaying.value) {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            viewModel.handleCountDownTimer()
        }
    }

    FullScreenProgressIndicator(challenge.progress)

    if (challenge.finished) {
        navController.navigate(Screen.Reward.route)
    }
}