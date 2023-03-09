package com.example.healthgrind.data

import androidx.lifecycle.MutableLiveData

enum class ExerciseType {
    RUN, WALK, STRENGTH, OUTDOOR
}

enum class GameType {
    SMASH, FORTNITE, VALORANT, TEKKEN, STARDEW_VALLEY
}

enum class DifficultyType {
    BEGINNER, ADVANCED, PRO;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}

enum class GenderType {
    MALE, FEMALE, OTHER;

    companion object {
        fun getList(): List<String> {
            return values().map {
                it.toString()
            }
        }
    }
}


data class GameModel(
    val id: Int,
    val name: String,
    val image: Int,
    val challenges: List<ChallengeModel> = listOf()
)

data class ChallengeModel(
    val key: Int,
    var title: String,
    val reward: RewardModel,
    val icon: Int,
    val exerciseType: ExerciseType,
    val difficulty: DifficultyType,
    val gameType: GameType,
    val goal: Int,
    var finished: MutableLiveData<Boolean> = MutableLiveData(false)
)

data class RewardModel(
    val name: String,
    val value: Int = 0,
    val code: String = "",
    val image: Int
)