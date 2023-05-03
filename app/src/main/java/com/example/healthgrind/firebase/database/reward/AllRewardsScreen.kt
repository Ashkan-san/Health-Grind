package com.example.healthgrind.firebase.database.reward

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.material.MaterialTheme
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllRewardsScreen(
    navController: NavHostController,
    rewardViewModel: RewardViewModel = hiltViewModel(),
    challengeViewModel: ChallengeViewModel
) {
    val rewards = rewardViewModel.rewards.collectAsState(initial = emptyList())

    val mandatoryCount = challengeViewModel.completedMandatoryCount.value

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
            RewardChip(
                reward = reward,
                onRedeem = { rewardViewModel.onUpdateReward(reward, index, mandatoryCount) }
            )
        }
    }
}