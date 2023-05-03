package com.example.healthgrind.firebase.database.challenge

import androidx.compose.runtime.mutableStateOf
import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {
    // USE CASES
    // - alle kraft/run/walk/outdoor challenges geben
    // - ggf. noch filtern nach difficulty und gametype
    // - eine Challenge abschließen
    // - eine Challenge aktualisieren (Fortschritt)

    var challenges: Flow<List<NewChallenge>>? = null
    val currentChallengeState = mutableStateOf(NewChallenge())
    var progress = mutableStateOf(0F)

    var completedMandatoryCount = mutableStateOf(0)
    var completedOptionalCount = mutableStateOf(0)

    // DIE AUSGEWÄHLTE CHALLENGE SETZEN
    fun setCurrentChallenge(challenge: NewChallenge) {
        currentChallengeState.value = challenge
    }

    // BEI KNOPFDRUCK UM 1 ERHÖHEN, ABER LIEBER MIT SENSORERKENNUNG
    fun increaseCurrent(challenge: NewChallenge) {
        if (challenge.current < (challenge.goal)) {
            // ADD 1
            currentChallengeState.value = challenge.copy(current = challenge.current + 1)

            // PROGRESS FÜR INDIKATOR AKTUALISIEREN
            if (challenge.current != 0) {
                progress.value =
                    challenge.current.toFloat().div(challenge.goal.toFloat())
            }
        } else {
            // Challenge auf finished setzen
            // Completed Challenges um 1 erhöhen, jenachdem ob mandatory oder nicht
            currentChallengeState.value.finished = true
            if (challenge.mandatory) completedMandatoryCount.value += 1 else completedOptionalCount.value += 1
        }
        // UPDATE CHALLENGE IN DATABASE
        launchCatching { storageService.updateChallenge(currentChallengeState.value) }
    }

    // Nach ExerciseType usw. filtern
    fun filterChallengesAndGet(exerciseType: String) {
        launchCatching {
            challenges = storageService.getSpecificChallenges(exerciseType)
        }
    }
}