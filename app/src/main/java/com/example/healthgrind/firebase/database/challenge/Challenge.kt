package com.example.healthgrind.firebase.database.challenge

import com.example.healthgrind.R
import com.example.healthgrind.support.ExerciseType
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Challenge(
    @DocumentId val id: String = "",

    var title: String = "",
    val mandatory: Boolean = false,
    val icon: Int = if (mandatory) R.drawable.euro else R.drawable.heart,

    val exerciseType: ExerciseType = ExerciseType.WALK,
    //val difficulty: SkillType,
    //val gameType: GameType,

    var current: Int = 0,
    val goal: Int = 0,
    var progress: Float = 0F,
    var finished: Boolean = false,

    var timeStampStart: Timestamp = Timestamp(Date(123, 1, 1)),
    var timeStampFinish: Timestamp = Timestamp(Date(123, 1, 1))
)