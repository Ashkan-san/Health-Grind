package com.example.healthgrind.firebase.database

import com.example.healthgrind.firebase.auth.AccountService
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

    /*override suspend fun deleteAllForUser(userId: String) {
        val matchingTasks = currentCollection(userId).get().await()
        matchingTasks.map { it.reference.delete().asDeferred() }.awaitAll()
    }*/

    // TODO noch ändern, aktuell zum testen auf Reward
    private fun currentRewardCollection(): CollectionReference =
        firestore.collection(FORTNITE_REWARDS)

    companion object {
        private const val USER_COLLECTION = "users"
        private const val CHALLENGE_COLLECTION = "challenges"

        private const val REWARD_COLLECTION = "rewards"
        private const val FORTNITE_REWARDS = "fortnite"
    }
}