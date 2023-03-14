package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Card
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen
import java.util.*

@Composable
fun PlayerInfoScreen(navController: NavHostController, pref: SharedPreferences) {
    val listState = rememberScalingLazyListState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState,
    ) {
        item {
            Image(
                painter = painterResource(pref.getInt("picture", R.drawable.ash)),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
        }
        item {
            Text(text = pref.getString("name", "Name_Placeholder")!!)
        }
        item {
            Text(text = "Level: " + pref.getInt("level", 0))
        }

        item {
            Card(modifier = Modifier.fillMaxWidth(), enabled = false, onClick = {}) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    pref.all.forEach { (key, value) ->
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
                                    "Skill-Level" -> navController.navigate(Screen.SkillInput.route)
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