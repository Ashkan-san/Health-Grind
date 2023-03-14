package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.database.reward.Reward
import kotlinx.coroutines.flow.Flow

interface StorageService {

    // TODO was genau speichern?
    // Challenges
    // Sportdata (Schritte, Herzschlag...)
    // Userdata (Name, Alter, Gewicht...)
    // Games? Werden ja nicht geändert...

    // CHALLENGES
    val challenges: Flow<List<Challenge>>

    suspend fun getChallenge(challengeId: String): Challenge?
    suspend fun saveChallenge(challenge: Challenge): String
    suspend fun updateChallenge(challenge: Challenge)
    suspend fun deleteChallenge(challengeId: String)
    //suspend fun deleteAllForUser(userId: String)


    // REWARDS
    val rewards: Flow<List<Reward>>

    suspend fun getReward(rewardId: String): Reward?
    suspend fun saveReward(reward: Reward): String
    suspend fun updateReward(reward: Reward)
    suspend fun deleteReward(rewardId: String)
    //suspend fun deleteAllForUser(userId: String)
}