package com.example.healthgrind.data

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.healthgrind.firebase.database.challenge.NewChallenge
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.concurrent.TimeUnit

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
        println("BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
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
        println("BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
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