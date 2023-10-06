package com.example.healthgrind.firebase.database.challenge

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.support.ExerciseType
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.myFormatTime

@Composable
fun ChallengeChip(
    item: Challenge,
    viewModel: ChallengeViewModel,
    navController: NavHostController,
    vibrator: Vibrator
) {
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
                contentDescription = "Icon",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        },
        onClick = {
            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
            viewModel.setCurrentChallenge(item)

            when (item.exerciseType) {
                ExerciseType.STRENGTH -> {
                    navController.navigate(Screen.Strength.route)
                }

                ExerciseType.WALK -> {
                    navController.navigate(Screen.Walk.route)
                }

                ExerciseType.RUN -> {
                    navController.navigate(Screen.Run.route)
                }

                ExerciseType.OUTDOOR -> {
                    //navController.navigate(Screen.Outdoor.route)
                }
            }
        },
        enabled = !item.finished
    )
}

fun checkGoalType(exerciseType: ExerciseType): Boolean {
    if (exerciseType == ExerciseType.RUN || exerciseType == ExerciseType.OUTDOOR) {
        return true
    }
    return false
}