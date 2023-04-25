package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.auth.register.User
import com.example.healthgrind.firebase.database.challenge.NewChallenge
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.reward.NewReward
import kotlinx.coroutines.flow.Flow

interface StorageService {
    // USERS
    val users: Flow<List<User>>

    suspend fun getCurrentUser(): User?
    suspend fun saveCurrentUser(user: User)
    suspend fun updateCurrentUser(field: String, value: Any)

    // GAMES
    val platforms: Flow<List<Platform>>

    suspend fun getGame(id: String): Platform?
    suspend fun saveGame(platform: Platform)
    suspend fun updateGame(platform: Platform)

    // CHALLENGES
    val challenges: Flow<List<NewChallenge>>

    suspend fun getChallenge(id: String): NewChallenge?
    suspend fun saveChallenge(challenge: NewChallenge)
    suspend fun updateChallenge(challenge: NewChallenge)

    // REWARDS
    val rewards: Flow<List<NewReward>>

    suspend fun getReward(id: String): NewReward?
    suspend fun saveReward(reward: NewReward)
    suspend fun updateReward(reward: NewReward)
}