package com.example.healthgrind.firebase.database.challenge

import android.os.Vibrator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

@Composable
fun ChallengesScreen(
    navController: NavHostController,
    platformId: String?,
    exerciseId: String?,
    viewModel: ChallengeViewModel,
    vibrator: Vibrator
) {
    viewModel.filterChallengesAndSet(exerciseId!!)
    val challengesList = viewModel.challenges!!.collectAsState(initial = emptyList())

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = rememberScalingLazyListState()

    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Challenges"
            )
        }

        itemsIndexed(challengesList.value) { index, item ->
            ChallengeChip(item, viewModel, navController, vibrator)
        }
    }

}
