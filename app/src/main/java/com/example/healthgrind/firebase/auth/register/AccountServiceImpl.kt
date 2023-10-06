package com.example.healthgrind.firebase.auth.register

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
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
                { User(it.uid, it.email!!) } ?: User())
            }

            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

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
}