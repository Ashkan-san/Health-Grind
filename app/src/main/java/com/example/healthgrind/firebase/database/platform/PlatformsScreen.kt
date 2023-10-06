package com.example.healthgrind.firebase.database.platform

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.loadPlatformImageFromStorage

@Composable
fun PlatformsScreen(
    navController: NavHostController,
    viewModel: PlatformViewModel = hiltViewModel(),
    vibrator: Vibrator
) {
    val context = LocalContext.current
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
                        model =
                        ImageRequest.Builder(context)
                            .data(loadPlatformImageFromStorage(item.image))
                            //.networkCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCacheKey(item.image)
                            .memoryCacheKey(item.image)
                            .build(),
                        contentScale = ContentScale.None
                    )
                ),
                label = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = item.name
                    )
                },

                onClick = {
                    vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                    navController.navigate("${Screen.Exercises.route}/${item.id}")
                }
            )

        }
    }
}

