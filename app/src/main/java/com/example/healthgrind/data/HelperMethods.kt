package com.example.healthgrind.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.MaterialTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.healthgrind.firebase.database.challenge.NewChallenge
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.concurrent.TimeUnit

@Composable
fun FullScreenProgressIndicator(
    progress: Float
) {
    // PROGRESS INDICATOR
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            // in 360° ab 3 Uhr
            //startAngle = 315f,
            //endAngle = 225f,
            trackColor = MaterialTheme.colors.primary,
            indicatorColor = MaterialTheme.colors.primaryVariant,
            strokeWidth = 10.dp,
            // Progress von 0.0 bis 1.0
            progress = progress,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 1.dp)
        )
    }
}

fun myFormatTime(value: Long): String = String.format(
    "%02d:%02d",
    TimeUnit.MILLISECONDS.toMinutes(value),
    TimeUnit.MILLISECONDS.toSeconds(value) % 60
)

@Composable
fun LoadImageFromStorage(imagePath: String, modifier: Modifier) {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(imagePath).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        Log.d(TAG, "BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(link)
            .build(),
        contentDescription = "Image",
        modifier = modifier
    )
}

@Composable
fun loadPlatformImageFromStorage(imagePath: String): String {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(imagePath).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        Log.d(TAG, "BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
    }

    return link
}

fun createAllChallengesForUser(): List<NewChallenge> {
    return listOf(
        NewChallenge(
            title = "Schritte",
            mandatory = false,
            exerciseType = ExerciseType.WALK,
            goal = 2000,
        ),
        NewChallenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 2000,
        ),
        NewChallenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 4000,
        ),
        NewChallenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 4000,
        ),
        NewChallenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 15,
        ),
        NewChallenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 15,
        ),
        NewChallenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 30,
        ),
        NewChallenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 30,
        ),
        NewChallenge(
            title = "Push Ups",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
        NewChallenge(
            title = "Squats",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
    )
}