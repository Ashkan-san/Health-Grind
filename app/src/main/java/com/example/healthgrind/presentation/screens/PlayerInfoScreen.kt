package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.firebase.auth.register.AccountViewModel
import com.example.healthgrind.support.LoadImageFromStorage
import com.example.healthgrind.support.Screen

@Composable
fun PlayerInfoScreen(
    navController: NavHostController,
    viewModel: AccountViewModel = hiltViewModel()
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
            Text(text = user.name, color = MaterialTheme.colors.secondaryVariant)
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
                            label = { Text(key) },
                            secondaryLabel = {
                                var text = ""
                                text = when (value) {
                                    "MALE" -> "Mann"
                                    "FEMALE" -> "Frau"
                                    "OTHER" -> "Divers"
                                    "BEGINNER" -> "Anfänger"
                                    "ADVANCED" -> "Erfahrener"
                                    "PRO" -> "Profi"
                                    else -> value.toString()
                                }
                                Text(text)
                            },
                            onClick = {
                                when (key) {
                                    "Name" -> navController.navigate(Screen.NameInput.route)
                                    "Alter" -> navController.navigate(Screen.AgeInput.route)
                                    "Gewicht" -> navController.navigate(Screen.WeightInput.route)
                                    "Größe" -> navController.navigate(Screen.HeightInput.route)
                                    "Geschlecht" -> navController.navigate(Screen.GenderInput.route)
                                    "Sport-Level" -> navController.navigate(Screen.SkillInput.route)
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