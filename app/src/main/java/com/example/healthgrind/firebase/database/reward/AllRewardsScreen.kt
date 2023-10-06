package com.example.healthgrind.firebase.database.reward

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel


@Composable
fun AllRewardsScreen(
    navController: NavHostController,
    rewardViewModel: RewardViewModel = hiltViewModel(),
    viewModel: ChallengeViewModel,
    vibrator: Vibrator
) {
    val rewards = rewardViewModel.rewards.collectAsState(initial = emptyList())
    val statisticsState = viewModel.statisticsState.value

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    )
    {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Rewards"
            )
        }

        // BUTTON DER REWARD AKTUALISIERT
        itemsIndexed(rewards.value) { index, reward ->
            rewardViewModel.onUnlockReward(reward, index, statisticsState.mandatoryCount)
            RewardChip(
                reward = reward,
                onRedeem = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    rewardViewModel.onRedeemReward(reward)
                }
            )
        }

        item {
            val annotatedText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondaryVariant,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("${statisticsState.mandatoryCount}/${statisticsState.mandatoryGoal} ")
                }
                append("zum nächsten Reward")
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = annotatedText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}