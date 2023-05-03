package com.example.healthgrind.data

import com.example.healthgrind.firebase.database.challenge.Challenge

enum class ExerciseType {
    RUN, WALK, STRENGTH, OUTDOOR
}

enum class GameType {
    SMASH, FORTNITE, VALORANT, TEKKEN, STARDEW_VALLEY
}

enum class PlatformType {
    NINTENDO, PLAYSTATION, STEAM, RIOT, PLAYSTORE, APPSTORE, FORTNITE, WARZONE
}

enum class SkillType {
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

enum class ProfileType {
    ARDIAN, ARIYAN, BRIAN, GERRIT, JILL, JOSEPH, JUSTUS, KJELL, OLIVER, QUAN;

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
    val challenges: List<Challenge> = listOf()
)

data class RewardModel(
    val name: String,
    val value: Int = 0,
    val code: String = "",
    val image: Int
)