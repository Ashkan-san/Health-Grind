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
import com.example.healthgrind.data.FullScreenProgressIndicator
import com.example.healthgrind.data.myFormatTime
import com.example.healthgrind.presentation.AutoResizingText

@Composable
fun RunningScreen(
    mainViewModel: MainViewModel,
    filChallIndex: String?,
    navController: NavHostController
) {
    val time by mainViewModel.time.observeAsState(0L)
    val progress by mainViewModel.progress.observeAsState(1.00F)
    val isPlaying by mainViewModel.isPlaying.observeAsState(false)

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
                text = "Run",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        item {
            CountDownIndicator(time = time)
        }
        item {
            CountDownButton(isPlaying = isPlaying) {
                mainViewModel.handleCountDownTimer()
            }
        }
    }
    FullScreenProgressIndicator(progress = progress)
    //disableChallengeChip(weiter, dataSource, filChallIndex!!, mainViewModel, navController)
}

@Composable
fun CountDownIndicator(
    time: Long,
) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val timeString = myFormatTime(time)
        AutoResizingText(text = timeString)
    }
}

@Composable
fun CountDownButton(
    isPlaying: Boolean,
    optionSelected: () -> Unit
) {
    Button(
        onClick = {
            optionSelected()
        }, modifier = Modifier.size(50.dp)
    )

    {
        val icon: Int = if (!isPlaying) {
            com.example.healthgrind.R.drawable.ic_play
        } else {
            com.example.healthgrind.R.drawable.ic_pause
        }
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "Pay/Pause Button",
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colors.primaryVariant)
        )
    }
}