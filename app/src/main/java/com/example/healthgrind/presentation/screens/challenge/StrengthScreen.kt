package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.presentation.AutoResizingText
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.viewmodel.MainViewModel

@Composable
fun StrengthScreen(
    mainViewModel: MainViewModel,
    filChallIndex: String?,
    navController: NavHostController,
    dataSource: DataSource
) {
    // TODO wieder ändern
    var filteredChallenges = emptyArray<Challenge>()
    val challenge: Challenge = filteredChallenges[filChallIndex!!.toInt()]

    val reps by mainViewModel.reps.observeAsState(0F)
    val goal by mainViewModel.strengthGoal.observeAsState(0F)
    mainViewModel.setStrengthGoal(challenge.goal.toFloat())
    val progress by mainViewModel.strengthProgress.observeAsState(0.00F)

    val finish by challenge.finished.observeAsState(false)
    val weiter by mainViewModel.weiter.observeAsState(false)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = false

    ) {
        // TITLE
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primaryVariant,
                text = "Challenge",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "${challenge.goal} ${challenge.title}",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        // REPS
        item {
            RepsIndicator(reps = reps, goal = goal)
        }

        item {
            Button(
                onClick = {
                    if (!finish) {
                        mainViewModel.increaseReps()
                    }
                }, modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Plus Button",
                    modifier = Modifier
                        .size(50.dp)
                        .background(MaterialTheme.colors.primaryVariant)
                )
            }
        }
    }

    FullScreenProgressIndicator(progress = progress)

    disableChallengeChip(weiter, dataSource, filChallIndex, mainViewModel, navController)
}

fun disableChallengeChip(
    weiter: Boolean,
    dataSource: DataSource,
    filChallIndex: String,
    mainViewModel: MainViewModel,
    navController: NavHostController
) {
    if (weiter) {
        var bestIndex = 0
        dataSource.challenges.forEachIndexed { index, item ->
            // TODO wieder ändern
            var filteredChallenges = emptyArray<Challenge>()
            if (item.key == filteredChallenges[filChallIndex.toInt()].key) {
                bestIndex = index
            }
        }

        dataSource.challenges[bestIndex].finished.value = true
        mainViewModel.setWeiter(false)
        navController.navigate("${Screen.RewardDialog.route}/${filChallIndex}")
    }
}

@Composable
fun RepsIndicator(reps: Float, goal: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        AutoResizingText(text = "${reps.toInt()}/${goal.toInt()}")
    }
}