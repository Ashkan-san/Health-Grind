package com.example.healthgrind.presentation.screens

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
fun StartScreen(navController: NavHostController) {
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
                        navController.navigate(Screen.Games.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.games),
                            contentDescription = "Games"
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
        /*item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Made by:\n" +
                        "Ashkan, Joseph, Oliver",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                fontSize = 10.sp
            )
        }*/
    }
}

