package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.auth.register.User
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.firebase.database.challenge.Statistics
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.reward.Reward
import kotlinx.coroutines.flow.Flow

interface StorageService {
    // USERS
    val users: Flow<List<User>>

    suspend fun getCurrentUser(): User?
    suspend fun saveCurrentUser(user: User)
    suspend fun updateCurrentUser(field: String, value: Any)

    // GAMES
    val platforms: Flow<List<Platform>>

    suspend fun getPlatform(id: String): Platform?
    suspend fun savePlatform(platform: Platform)
    suspend fun updatePlatform(platform: Platform)

    // CHALLENGES
    val challenges: Flow<List<Challenge>>

    suspend fun getSpecificChallenges(exercise: String): Flow<List<Challenge>>
    suspend fun getChallenge(id: String): Challenge?
    suspend fun saveChallenge(challenge: Challenge)
    suspend fun updateChallenge(challenge: Challenge)

    suspend fun updateStats(stats: Statistics)
    suspend fun getStatistics(): Statistics?

    // REWARDS
    val rewards: Flow<List<Reward>>

    suspend fun getReward(id: String): Reward?
    suspend fun saveReward(reward: Reward)
    suspend fun updateReward(reward: Reward)
}