package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text
import com.example.healthgrind.data.LoadImageFromStorage
import com.example.healthgrind.firebase.auth.register.SignUpViewModel
import com.example.healthgrind.presentation.navigation.Screen
import java.util.*

@Composable
fun PlayerInfoScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    viewModel.getUserDataFromDb()
    val user = viewModel.userState.value
    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
    ) {
        item {
            LoadImageFromStorage(
                user.image,
                Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .wrapContentSize(align = Alignment.Center)
            )
        }
        item {
            Text(text = user.name)
        }
        item {
            Text(text = "Level: " + user.level)
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), enabled = false, onClick = {}) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    for ((key, value) in user.iterator()) {
                        Chip(
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(key.capitalize(Locale.ROOT)) },
                            secondaryLabel = { Text("$value") },
                            onClick = {
                                when (key) {
                                    "name" -> navController.navigate(Screen.NameInput.route)
                                    "age" -> navController.navigate(Screen.AgeInput.route)
                                    "weight" -> navController.navigate(Screen.WeightInput.route)
                                    "height" -> navController.navigate(Screen.HeightInput.route)
                                    "gender" -> navController.navigate(Screen.GenderInput.route)
                                    "skill" -> navController.navigate(Screen.SkillInput.route)
                                    else -> {}
                                }
                            }
                        )
                    }
                }
            }
        }
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}