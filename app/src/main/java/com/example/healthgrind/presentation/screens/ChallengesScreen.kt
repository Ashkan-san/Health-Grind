package com.example.healthgrind.presentation.screens

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.data.DifficultyType
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.data.GameType
import com.example.healthgrind.firebase.database.Challenge
import com.example.healthgrind.presentation.MapsLocationActivity
import com.example.healthgrind.presentation.WalkActivity
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.viewmodel.MainViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

var filteredChallenges = listOf<Challenge>()

fun getDocument(collection: String, documentPath: String) {
    val db = Firebase.firestore
    val docRef = db.collection(collection).document(documentPath)

    docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(ContentValues.TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(ContentValues.TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG, "get failed with ", exception)
        }
}

@Composable
fun ChallengesScreen(
    navController: NavHostController,
    dataSource: DataSource,
    id: String?,
    id2: String?,
    pref: SharedPreferences,
    mainViewModel: MainViewModel
) {
    val listState = rememberScalingLazyListState()
    val context = LocalContext.current
    var format = false

    filteredChallenges = dataSource.challenges

    getDocument("fortnite", "code-01")

    ScalingLazyColumn(
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
                text = "Challenges"
            )
        }
        // LIST ITEMS
        when (id) {
            "1" -> filteredChallenges =
                filteredChallenges.filter { c -> c.gameType == GameType.SMASH }
            "2" -> filteredChallenges =
                filteredChallenges.filter { c -> c.gameType == GameType.FORTNITE }
            "3" -> filteredChallenges =
                filteredChallenges.filter { c -> c.gameType == GameType.STARDEW_VALLEY }
            "4" -> filteredChallenges =
                filteredChallenges.filter { c -> c.gameType == GameType.TEKKEN }
            "5" -> filteredChallenges =
                filteredChallenges.filter { c -> c.gameType == GameType.VALORANT }
            else -> {}
        }

        when (pref.getString("Skill-Level", "")) {
            "BEGINNER" -> {
                filteredChallenges =
                    filteredChallenges.filter { c -> c.difficulty == DifficultyType.BEGINNER }
            }
            "ADVANCED" -> {
                filteredChallenges = filteredChallenges.filter { c ->
                    c.difficulty == DifficultyType.BEGINNER ||
                            c.difficulty == DifficultyType.ADVANCED

                }
            }
            "PRO" -> {
                filteredChallenges = filteredChallenges.filter { c ->
                    c.difficulty == DifficultyType.BEGINNER ||
                            c.difficulty == DifficultyType.ADVANCED ||
                            c.difficulty == DifficultyType.PRO
                }
            }
        }

        when (id2) {
            "RUN" -> {
                filteredChallenges =
                    filteredChallenges.filter { c -> c.exerciseType == ExerciseType.RUN }
                format = true
            }
            "WALK" -> filteredChallenges =
                filteredChallenges.filter { c -> c.exerciseType == ExerciseType.WALK }
            "STRENGTH" -> filteredChallenges =
                filteredChallenges.filter { c -> c.exerciseType == ExerciseType.STRENGTH }
            "OUTDOOR" -> {
                filteredChallenges =
                    filteredChallenges.filter { c -> c.exerciseType == ExerciseType.OUTDOOR }
                format = true
            }
        }

        itemsIndexed(filteredChallenges) { index, item ->
            Chip(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                label = {
                    var goalText = item.goal.toString()
                    if (format) {
                        goalText = myFormatTime(item.goal.toLong())
                    }
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onPrimary,
                        text = "$goalText ${item.title}"
                    )
                },
                secondaryLabel = {
                    Text(
                        text = item.reward.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = "Icon Name",
                        modifier = Modifier
                            .size(24.dp)
                            .wrapContentSize(align = Alignment.Center)
                    )
                },
                onClick = {
                    when (id2) {
                        "RUN" -> {
                            mainViewModel.setTimeGoal(filteredChallenges[index].goal.toLong())
                            navController.navigate("${Screen.Running.route}/${index}")
                        }

                        "WALK" -> context.startActivity(
                            Intent(context, WalkActivity::class.java).putExtra(
                                "index",
                                index.toString()
                            )
                        )
                        "STRENGTH" -> navController.navigate("${Screen.Strength.route}/${index}")
                        "OUTDOOR" -> context.startActivity(
                            Intent(
                                context,
                                MapsLocationActivity::class.java
                            )
                        )
                    }
                },
                enabled = !item.finished.value!!
            )
        }
    }

}