package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun GamesScreen(navController: NavHostController, dataSource: DataSource) {
    val listState = rememberScalingLazyListState()
    val games = dataSource.games

    ScalingLazyColumn(
        // LIST PARAMETER
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState

    ) {
        // TITLE
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Games"
            )
        }
        // LIST ITEMS
        items(games) { index ->
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),

                colors = ChipDefaults.imageBackgroundChipColors(
                    backgroundImagePainter = painterResource(id = index.image)
                ),

                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = index.name
                    )
                },

                onClick = { navController.navigate("${Screen.Exercises.route}/${index.id}") }
            )

        }
    }
}