package com.example.healthgrind.firebase.auth.register

import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)

    //fun updateUser(name: String, age: Int, height: Int, weight: Int, gender: GenderType, level: Int, skill: SkillType)
    fun updateUser(field: String, value: Any)
    fun createUser(email: String)
    //suspend fun sendRecoveryEmail(email: String)
    //suspend fun deleteAccount()
    //suspend fun signOut()
}