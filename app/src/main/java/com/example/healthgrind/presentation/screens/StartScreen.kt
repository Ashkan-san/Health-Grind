package com.example.healthgrind.presentation.screens

import android.app.usage.UsageStatsManager
import android.content.SharedPreferences
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import com.example.healthgrind.R
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel
import com.example.healthgrind.support.Screen

@Composable
fun StartScreen(
    navController: NavHostController,
    pref: SharedPreferences,
    viewModel: ChallengeViewModel,
    vibrator: Vibrator,
    usageStatsManager: UsageStatsManager
) {
    pref.edit().putBoolean("firstStart", false).apply()
    viewModel.getStatisticsFromFirestore()

    viewModel.updateUsageTime(usageStatsManager)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(100.dp)
        )
        Row(
            modifier = Modifier.padding(bottom = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            //verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    navController.navigate(Screen.PlayerInfo.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.player),
                    contentDescription = "Player Info"
                )
            }

            Button(
                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    navController.navigate(Screen.Platforms.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.platform),
                    contentDescription = "Platforms"
                )
            }

            Button(
                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    navController.navigate(Screen.AllRewards.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.reward),
                    contentDescription = "Rewards"
                )
            }

            /*Button(
                onClick = {
                    val vibrationEffect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
                    vibrator.vibrate(vibrationEffect)
                    navController.navigate(Screen.Debug.route)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.debug),
                    contentDescription = "Debug"
                )
            }*/
        }
    }
}

