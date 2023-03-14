package com.example.healthgrind.firebase.database

import androidx.lifecycle.MutableLiveData
import com.example.healthgrind.data.DifficultyType
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.data.GameType
import com.example.healthgrind.data.RewardModel
import com.google.firebase.firestore.DocumentId

data class Challenge(
    @DocumentId val id: String = "",
    val key: Int,
    var title: String,
    val reward: RewardModel,
    val icon: Int,
    val exerciseType: ExerciseType,
    val difficulty: DifficultyType,
    val gameType: GameType,
    val goal: Int,
    // TODO zu normalen boolean ändern
    var finished: MutableLiveData<Boolean> = MutableLiveData(false)
)