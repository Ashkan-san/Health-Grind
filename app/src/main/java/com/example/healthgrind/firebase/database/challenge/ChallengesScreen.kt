package com.example.healthgrind.firebase.database.challenge

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.data.myFormatTime
import com.example.healthgrind.presentation.MapsLocationActivity
import com.example.healthgrind.presentation.WalkActivity
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.viewmodel.MainViewModel

@Composable
fun ChallengesScreen(
    navController: NavHostController,
    platformId: String?,
    exerciseId: String?,
    mainViewModel: MainViewModel,
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    val listState = rememberScalingLazyListState()
    val context = LocalContext.current

    val challengesList = viewModel.challenges.collectAsState(initial = emptyList())
    viewModel.filterChallengesAndGet(exerciseId!!)

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState

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
            var goalText = item.goal.toString()
            var currentText = item.current.toString()
            val format = checkGoalType(item.exerciseType)

            Chip(
                colors = ChipDefaults.chipColors(
                    backgroundColor = if (item.mandatory) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = {
                    if (format) {
                        goalText = myFormatTime(item.goal.toLong())
                        currentText = myFormatTime(item.current.toLong())
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = "$goalText ${item.title}"
                    )
                },
                secondaryLabel = {
                    Text(
                        text = "$currentText / $goalText",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "Icon Name",
                        modifier = Modifier
                            .size(24.dp)
                            .wrapContentSize(align = Alignment.Center)
                    )
                },
                onClick = {
                    when (item.exerciseType) {
                        ExerciseType.RUN -> {
                            //mainViewModel.setTimeGoal(filteredChallenges[index].goal.toLong())
                            navController.navigate("${Screen.Running.route}/${index}")
                        }

                        ExerciseType.WALK -> context.startActivity(
                            Intent(context, WalkActivity::class.java).putExtra(
                                "index",
                                index.toString()
                            )
                        )
                        ExerciseType.STRENGTH -> navController.navigate("${Screen.Strength.route}/${index}")
                        ExerciseType.OUTDOOR -> context.startActivity(
                            Intent(
                                context,
                                MapsLocationActivity::class.java
                            )
                        )
                    }
                },
                enabled = !item.finished
            )
        }
    }

}

fun checkGoalType(exerciseType: ExerciseType): Boolean {
    if (exerciseType == ExerciseType.RUN || exerciseType == ExerciseType.OUTDOOR) {
        return true
    }
    return false
}