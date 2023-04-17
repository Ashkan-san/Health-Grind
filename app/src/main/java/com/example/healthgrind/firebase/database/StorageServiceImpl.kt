package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.auth.register.User
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.firebase.database.reward.Reward
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.snapshots
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
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
        private const val GAME_COLLECTION = "games"


        private const val FORTNITE_REWARDS = "fortnite"
    }

    // USERS
    override val users: Flow<List<User>>
        get() =
            currentUserCollection().snapshots()
                .map { snapshot -> snapshot.toObjects() }

    override suspend fun getCurrentUser(): User? =
        currentUserCollection().document(auth.currentUserId).get().await().toObject()

    override suspend fun saveUser(user: User): String =
        currentUserCollection().add(user).await().id

    override suspend fun updateUser(user: User) {
        currentUserCollection().document(user.id).set(user).await()
    }

    override suspend fun deleteUser(uid: String) {
        currentUserCollection().document(uid).delete().await()
    }

    private fun currentUserCollection(): CollectionReference =
        firestore.collection(USER_COLLECTION)


    // CHALLENGES
    override val challenges: Flow<List<Challenge>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                currentChallengeCollection(user.id).snapshots()
                    .map { snapshot -> snapshot.toObjects() }
            }

    override suspend fun getChallenge(challengeId: String): Challenge? =
        currentChallengeCollection(auth.currentUserId).document(challengeId).get().await()
            .toObject()

    override suspend fun saveChallenge(challenge: Challenge): String =
        currentChallengeCollection(auth.currentUserId).add(challenge).await().id

    override suspend fun updateChallenge(challenge: Challenge) {
        currentChallengeCollection(auth.currentUserId).document(challenge.id).set(challenge).await()
    }

    override suspend fun deleteChallenge(challengeId: String) {
        currentChallengeCollection(auth.currentUserId).document(challengeId).delete().await()
    }

    private fun currentChallengeCollection(uid: String): CollectionReference =
        firestore.collection(USER_COLLECTION).document(uid).collection(CHALLENGE_COLLECTION)

    // REWARDS
    override val rewards: Flow<List<Reward>>
        get() =
            auth.currentUser.flatMapLatest {
                currentRewardCollection().snapshots().map { snapshot -> snapshot.toObjects() }
            }

    override suspend fun getReward(rewardId: String): Reward? =
        currentRewardCollection().document(rewardId).get().await().toObject()

    override suspend fun saveReward(reward: Reward): String =
        currentRewardCollection().add(reward).await().id

    override suspend fun updateReward(reward: Reward) {
        currentRewardCollection().document(reward.id).set(reward).await()
    }

    override suspend fun deleteReward(rewardId: String) {
        currentRewardCollection().document(rewardId).delete().await()
    }

    // TODO ändern
    private fun currentRewardCollection(): CollectionReference =
        firestore.collection(FORTNITE_REWARDS)
}