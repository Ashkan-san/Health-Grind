package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.auth.register.User
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.firebase.database.reward.Reward
import kotlinx.coroutines.flow.Flow

interface StorageService {

    // TODO was genau speichern?
    // Challenges
    // Sportdata (Schritte, Herzschlag...)
    // Userdata (Name, Alter, Gewicht...)
    // Games? Werden ja nicht geändert...

    // USERS
    val users: Flow<List<User>>
    //val currentUser: Flow<User>

    suspend fun getCurrentUser(): User?
    suspend fun saveUser(user: User): String
    suspend fun updateUser(user: User)
    suspend fun deleteUser(uid: String)

    // CHALLENGES
    val challenges: Flow<List<Challenge>>

    suspend fun getChallenge(challengeId: String): Challenge?
    suspend fun saveChallenge(challenge: Challenge): String
    suspend fun updateChallenge(challenge: Challenge)
    suspend fun deleteChallenge(challengeId: String)

    // REWARDS
    val rewards: Flow<List<Reward>>

    suspend fun getReward(rewardId: String): Reward?
    suspend fun saveReward(reward: Reward): String
    suspend fun updateReward(reward: Reward)
    suspend fun deleteReward(rewardId: String)
}