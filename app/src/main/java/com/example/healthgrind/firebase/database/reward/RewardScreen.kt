package com.example.healthgrind.firebase.database.reward

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material.dialog.DialogDefaults
import com.example.healthgrind.R
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel
import com.example.healthgrind.support.AutoResizingText
import com.example.healthgrind.support.Screen


@Composable
fun RewardScreen(
    navController: NavHostController,
    viewModel: ChallengeViewModel,
    vibrator: Vibrator
) {
    viewModel.updateMandatoryGoal()
    val statisticsState = viewModel.statisticsState.value

    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
    Dialog(
        showDialog = true,
        onDismissRequest = { navController.navigate(Screen.Start.route) }
    ) {
        Confirmation(
            onTimeout = {
                navController.navigate(Screen.Start.route) {
                    popUpTo(0) { inclusive = true }
                }
            },
            durationMillis = DialogDefaults.ShortDurationMillis,
            //backgroundColor = MaterialTheme.colors.secondaryVariant
        ) {
            Image(
                painter = painterResource(R.drawable.dab),
                contentDescription = "Dab",
                //alignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.CenterHorizontally),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Challenge complete!",
                fontSize = 20.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                val text: String = if (viewModel.currentChallengeState.value.mandatory) {
                    "${statisticsState.mandatoryCount}/${statisticsState.mandatoryGoal}"
                } else {
                    "${statisticsState.optionalCount}"
                }
                AutoResizingText(
                    text = text,
                    color = MaterialTheme.colors.secondaryVariant
                )
            }
        }
    }
}
/*
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
}*/
