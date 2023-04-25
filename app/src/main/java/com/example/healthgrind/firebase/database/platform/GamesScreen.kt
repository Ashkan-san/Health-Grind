package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.platform.PlatformViewModel
import com.example.healthgrind.presentation.navigation.Screen
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
fun GamesScreen(
    navController: NavHostController,
    viewModel: PlatformViewModel = hiltViewModel()
) {
    val listState = rememberScalingLazyListState()
    val platformList = viewModel.platforms.collectAsState(initial = emptyList())

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
                text = "Platforms/Games"
            )
        }
        // LIST ITEMS
        items(platformList.value) { item ->
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),

                colors = ChipDefaults.imageBackgroundChipColors(
                    backgroundImagePainter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(loadPlatformImageFromStorage(item = item))
                            .build()
                    )
                ),
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = item.name
                    )
                },

                onClick = { navController.navigate("${Screen.Exercises.route}/${item.id}") }
            )

        }
    }
}

@Composable
fun loadPlatformImageFromStorage(item: Platform): String {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(item.image).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        println("DIGGA, BILD NICHT GEFUNDEN.")
    }

    return link
}