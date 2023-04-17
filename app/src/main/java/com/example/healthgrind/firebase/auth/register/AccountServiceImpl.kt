package com.example.healthgrind.firebase.auth.register

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    // GETTER UND SETTER AUTOGENERIERT IN KOTLIN
    // HIER WIRD GETTER ÜBERSCHRIEBEN VON currentUserId
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    // UNSER AKTUELLER USER
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                // Profil Info des Firebase Nutzers wird geholt und in ein User Objekt gelegt
                this.trySend(auth.currentUser?.let
                { User(it.uid, it.isAnonymous, it.email!!) } ?: User())
            }

            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override fun createUser(email: String) {
        // auth.currentUser!!.email!!
        val user = User(id = currentUserId, email = email)

        Firebase.firestore.collection("users").document(currentUserId).set(user)
            .addOnSuccessListener {
                Log.d(
                    TAG, "DocumentSnapshot successfully written!"
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    override fun updateUser(field: String, value: Any) {
        val userRef = Firebase.firestore.collection("users").document(currentUserId)

        userRef
            .update(field, value)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }

    /*override fun updateUser(name: String, age: Int, height: Int, weight: Int, gender: GenderType, level: Int, skill: SkillType) {
        val userRef = Firebase.firestore.collection("users").document(currentUserId)

        userRef
            .update(mapOf(
                "name" to name,
                "age" to age,
                "height" to height,
                "weight" to weight,
                "gender" to gender,
                "level" to level,
                "skill" to skill
            ))
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
    }*/

    // ANMELDEN
    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }

    // ANONYMEN ACC ERSTELLEN
    // Sind nützlich zum Testen, aber Person kann nur von einem Gerät aus anmelden und Daten gehen verloren nach Abmeldung
    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    // Mail und PW Strings nehmen, credential Objekt erstellen und mit aktuellen User verbinden
    override suspend fun linkAccount(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        auth.currentUser!!.linkWithCredential(credential).await()
    }
/*
    override suspend fun sendRecoveryEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()

        // Sign the user back in anonymously.
        createAnonymousAccount()
    }*/
}