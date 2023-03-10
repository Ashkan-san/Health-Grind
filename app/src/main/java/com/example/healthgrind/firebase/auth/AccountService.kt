package com.example.healthgrind.firebase.auth

import com.example.healthgrind.firebase.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    //suspend fun sendRecoveryEmail(email: String)
    //suspend fun deleteAccount()
    //suspend fun signOut()
}