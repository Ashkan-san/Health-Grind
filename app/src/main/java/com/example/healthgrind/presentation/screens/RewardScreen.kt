package com.example.healthgrind.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.dialog.DialogDefaults
import com.example.healthgrind.R
import com.example.healthgrind.firebase.database.Challenge
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.viewmodel.MainViewModel


@Composable
fun RewardScreen(
    mainViewModel: MainViewModel,
    navController: NavHostController,
    filChallIndex: String?
) {
    val showDialog by mainViewModel.showDialog.observeAsState(false)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        userScrollEnabled = false
    ) {
        item {
            Button(
                onClick = {
                    mainViewModel.updateDialog(true)
                },
                modifier = Modifier.size(90.dp),
                colors = ButtonDefaults.primaryButtonColors(backgroundColor = Color.Transparent)
            ) {
                ShakerButton()
            }

            if (showDialog) {
                val challenge: Challenge = filteredChallenges[filChallIndex!!.toInt()]

                Dialog(
                    showDialog = true,
                    onDismissRequest = {
                        mainViewModel.updateDialog(false)
                        navController.navigate(Screen.Start.route)
                    }
                ) {
                    Confirmation(
                        onTimeout = {
                            mainViewModel.updateDialog(false)
                            navController.navigate(Screen.Start.route)
                        },
                        icon = {
                            Image(
                                painter = painterResource(challenge.reward.image),
                                contentDescription = "Reward",
                                modifier = Modifier.size(100.dp)
                            )
                        },
                        durationMillis = DialogDefaults.ShortDurationMillis
                    ) {
                        if (challenge.reward.value != 0) {
                            Text(
                                text = "You got ${challenge.reward.value} ${challenge.reward.name}! \n" +
                                        "Unlock code: ${challenge.reward.code}",
                                textAlign = TextAlign.Center
                            )
                        } else
                            Text(
                                text = "Skin '${challenge.reward.name}' unlocked!",
                                textAlign = TextAlign.Center
                            )
                    }
                }
            }
        }

        item {
            Text("Click the shaker for your reward!", textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun ShakerButton() {
    val angleOffset = 30f
    val animation = rememberInfiniteTransition()
    val wiggleAnimation by animation.animateFloat(
        initialValue = -angleOffset,
        targetValue = angleOffset,
        animationSpec = infiniteRepeatable(
            animation = tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(R.drawable.whey),
        contentDescription = "Shaker",
        modifier = Modifier
            .size(70.dp)
            .rotate(wiggleAnimation)
    )
}