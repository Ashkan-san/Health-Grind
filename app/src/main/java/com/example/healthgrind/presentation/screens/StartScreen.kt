package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun StartScreen(navController: NavHostController, pref: SharedPreferences) {
    pref.edit().putBoolean("firstStart", false).apply()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        userScrollEnabled = false,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        item {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                userScrollEnabled = false
            ) {
                item {
                    Button(onClick = {
                        navController.navigate(Screen.Platforms.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.platform),
                            contentDescription = "Platforms"
                        )
                    }
                }
                item {
                    Button(onClick = {
                        navController.navigate(Screen.PlayerInfo.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.player),
                            contentDescription = "Player Info"
                        )
                    }
                }
                item {
                    Button(onClick = {
                        navController.navigate(Screen.Debug.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.debug),
                            contentDescription = "Debug"
                        )
                    }
                }
            }
        }
    }
}

