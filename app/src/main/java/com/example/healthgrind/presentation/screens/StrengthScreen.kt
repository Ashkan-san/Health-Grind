package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
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
import com.example.healthgrind.data.FullScreenProgressIndicator
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel
import com.example.healthgrind.presentation.AutoResizingText

@Composable
fun StrengthScreen(
    navController: NavHostController,
    viewModel: ChallengeViewModel
) {
    val challenge = viewModel.currentChallengeState.value
    val progress = viewModel.progress.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        userScrollEnabled = false

    ) {
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
        item {
            Box(
                modifier = Modifier
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                AutoResizingText(text = "${challenge.current}/${challenge.goal}")
            }
        }
        item {
            Button(
                onClick = {
                    if (!challenge.finished) {
                        viewModel.increaseCurrent(challenge)
                    }
                }, modifier = Modifier.size(50.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Plus",
                    modifier = Modifier
                        .size(50.dp)
                        .background(MaterialTheme.colors.primaryVariant)
                )
            }
        }
    }
    FullScreenProgressIndicator(progress)

    if (challenge.finished) {
        navController.navigate("${Screen.RewardDialog.route}")
    }

}
