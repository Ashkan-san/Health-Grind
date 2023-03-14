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
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.presentation.navigation.Screen
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun GamesScreen(navController: NavHostController, dataSource: DataSource) {
    val listState = rememberScalingLazyListState()
    val games = dataSource.games

    // STORAGE TEST
    val storage = Firebase.storage
    // Points to the root reference
    val storageRef = storage.reference
    // Points to "images"
    var imagesRef = storageRef.child("images")

    // Points to "images/space.jpg"
    // Note that you can use variables to create child values
    val fileName = "space.jpg"
    val spaceRef = imagesRef.child(fileName)

    // DATEIPFAD KRIEGEN "images/space.jpg"
    val path = spaceRef.path

    // DATEINAME KRIEGEN name is "space.jpg"
    val name = spaceRef.name

    // Points to "images"
    imagesRef = spaceRef.parent!!

    //val gsReference = storage.getReferenceFromUrl("gs://bucket/images/stars.jpg")

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
        items(games) { game ->
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),

                colors = ChipDefaults.imageBackgroundChipColors(
                    backgroundImagePainter = painterResource(id = game.image)
                ),

                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = game.name
                    )
                },

                onClick = { navController.navigate("${Screen.Exercises.route}/${game.id}") }
            )

        }
    }
}