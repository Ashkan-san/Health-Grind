package com.example.healthgrind.presentation.screens.challenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun ExercisesScreen(navController: NavHostController, id: String?) {
    val listState = rememberScalingLazyListState()

    // SCREEN AUFGEBAUT AUS 1 LAZYCOLUMN UND 2 LAZYROWS ALS KINDER
    ScalingLazyColumn(
        // LIST PARAMETER
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = false,
        state = listState
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Exercises"
            )
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                userScrollEnabled = false
            ) {
                item {
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Challenges.route}/${id}/${ExerciseType.RUN.name}")
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.sprint),
                            contentDescription = "Running"
                        )
                    }
                }
                item {
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Challenges.route}/${id}/${ExerciseType.WALK.name}")
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.walk),
                            contentDescription = "Walking"
                        )
                    }
                }
            }
        }

        item {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                userScrollEnabled = false
            ) {
                item {
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Challenges.route}/${id}/${ExerciseType.STRENGTH.name}")
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.strength),
                            contentDescription = "Strength"
                        )
                    }
                }
                item {
                    Button(
                        onClick = {
                            navController.navigate("${Screen.Challenges.route}/${id}/${ExerciseType.OUTDOOR.name}")
                        }) {
                        Icon(
                            painter = painterResource(R.drawable.outdoor),
                            contentDescription = "Outdoor"
                        )
                    }
                }
            }
        }
    }
}