package com.example.healthgrind.firebase.database.challenge

import androidx.lifecycle.MutableLiveData
import com.example.healthgrind.R
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.data.GameType
import com.example.healthgrind.data.RewardModel
import com.example.healthgrind.data.SkillType
import com.google.firebase.firestore.DocumentId

data class Challenge(
    @DocumentId val id: String = "",
    val key: Int,
    var title: String,
    val reward: RewardModel,
    val icon: Int,
    val exerciseType: ExerciseType,
    val difficulty: SkillType,
    val gameType: GameType,
    val goal: Int,
    // TODO zu normalen boolean ändern
    var finished: MutableLiveData<Boolean> = MutableLiveData(false)
)

data class NewChallenge(
    @DocumentId val id: String = "",

    var title: String = "",
    val mandatory: Boolean = false,
    val icon: Int = if (mandatory) R.drawable.euro else R.drawable.heart,

    val exerciseType: ExerciseType = ExerciseType.WALK,
    //val difficulty: SkillType,
    //val gameType: GameType,

    var current: Int = 0,
    val goal: Int = 0,
    var finished: Boolean = false,
) /*{
    companion object {
        private val count: AtomicInteger = AtomicInteger(1)
    }
    val id = count.incrementAndGet()
}*/