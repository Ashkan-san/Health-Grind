package com.example.healthgrind.firebase.database

import android.content.ContentValues.TAG
import android.util.Log
import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.auth.register.User
import com.example.healthgrind.firebase.database.challenge.NewChallenge
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.reward.NewReward
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {

    // COLLECTION STRINGS
    companion object {
        private const val USER_COLLECTION = "users"
        private const val CHALLENGE_COLLECTION = "challenges"
        private const val REWARD_COLLECTION = "rewards"
        private const val PLATFORM_COLLECTION = "platforms"
    }

    // USERS
    override val users: Flow<List<User>>
        get() =
            firestore.collection(USER_COLLECTION).snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getCurrentUser(): User? =
        currentUserDocument().get().await().toObject()

    override suspend fun saveCurrentUser(user: User) {
        currentUserDocument().set(user)
            .addOnSuccessListener { Log.d(TAG, "USER successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }.await()
    }

    override suspend fun updateCurrentUser(field: String, value: Any) {
        currentUserDocument().update(field, value)
            .addOnSuccessListener { Log.d(TAG, "USER (${field}, ${value}) successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }.await()
    }

    private fun currentUserDocument(): DocumentReference =
        firestore.collection(USER_COLLECTION).document(auth.currentUserId)

    // PLATFORMS/GAMES
    override val platforms: Flow<List<Platform>>
        get() =
            currentPlatformCollection().snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun getPlatform(id: String): Platform? =
        currentPlatformCollection().document(id).get().await().toObject()

    override suspend fun savePlatform(platform: Platform) {
        currentPlatformCollection().add(platform)
            .addOnSuccessListener { Log.d(TAG, "PLATFORM successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }.await()
    }

    override suspend fun updatePlatform(platform: Platform) {
        currentPlatformCollection().document(platform.id).set(platform)
            .addOnSuccessListener { Log.d(TAG, "PLATFORM successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }.await()
    }

    private fun currentPlatformCollection(): CollectionReference =
        currentUserDocument().collection(PLATFORM_COLLECTION)


    // CHALLENGES
    override val challenges: Flow<List<NewChallenge>>
        get() =
            allChallengesCollection().snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getChallenge(id: String): NewChallenge? =
        allChallengesCollection().document(id).get().await().toObject()

    override suspend fun getSpecificChallenges(exercise: String): Flow<List<NewChallenge>> {
        return allChallengesCollection().whereEqualTo("exerciseType", exercise)
            .snapshots().map { snapshot -> snapshot.toObjects() }
    }

    override suspend fun saveChallenge(challenge: NewChallenge) {
        allChallengesCollection().add(challenge)
            .addOnSuccessListener { Log.d(TAG, "CHALLENGE successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }.await()
    }

    override suspend fun updateChallenge(challenge: NewChallenge) {
        allChallengesCollection().document(challenge.id).set(challenge)
            .addOnSuccessListener { Log.d(TAG, "CHALLENGE successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }.await()
    }

    private fun allChallengesCollection(): CollectionReference =
        currentUserDocument().collection(CHALLENGE_COLLECTION)


    // REWARDS
    override val rewards: Flow<List<NewReward>>
        get() =
            currentRewardCollection().snapshots().map { snapshot -> snapshot.toObjects() }


    override suspend fun getReward(id: String): NewReward? =
        currentRewardCollection().document(id).get().await().toObject()

    override suspend fun saveReward(reward: NewReward) {
        currentRewardCollection().add(reward)
            .addOnSuccessListener { Log.d(TAG, "REWARD successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }.await()
    }

    override suspend fun updateReward(reward: NewReward) {
        currentRewardCollection().document(reward.id).set(reward)
            .addOnSuccessListener { Log.d(TAG, "REWARD successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }.await()
    }

    private fun currentRewardCollection(): CollectionReference =
        currentUserDocument().collection(REWARD_COLLECTION)


    // EXTRA DATA
}