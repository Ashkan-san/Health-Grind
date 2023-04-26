/*
package com.example.healthgrind.firebase.database

import android.content.Intent
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
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.presentation.MapsLocationActivity
import com.example.healthgrind.presentation.WalkActivity
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.firebase.database.challenge.getFilteredChallenges
import com.example.healthgrind.presentation.screens.myFormatTime

@Composable
fun ChallengeItem(
    challenge: Challenge
) {
    Chip(
    modifier = Modifier
    .fillMaxWidth()
    .padding(top = 10.dp),
    label = {
        var goalText = challenge.goal.toString()
        if (format) {
            goalText = myFormatTime(item.goal.toLong())
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colors.onPrimary,
            text = "$goalText ${challenge.title}"
        )
    },
    secondaryLabel = {
        Text(
            text = challenge.reward.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    },
    icon = {
        Icon(
            painter = painterResource(id = challenge.icon),
            contentDescription = "Icon Name",
            modifier = Modifier
                .size(24.dp)
                .wrapContentSize(align = Alignment.Center)
        )
    },
    onClick = {
        when (id2) {
            "RUN" -> {
                mainViewModel.setTimeGoal(filteredChallenges[index].goal.toLong())
                navController.navigate("${Screen.Running.route}/${index}")
            }

            "WALK" -> context.startActivity(
                Intent(context, WalkActivity::class.java).putExtra(
                    "index",
                    index.toString()
                )
            )
            "STRENGTH" -> navController.navigate("${Screen.Strength.route}/${index}")
            "OUTDOOR" -> context.startActivity(
                Intent(
                    context,
                    MapsLocationActivity::class.java
                )
            )
        }
    },
    enabled = !item.finished.value!!
    )
}*/
