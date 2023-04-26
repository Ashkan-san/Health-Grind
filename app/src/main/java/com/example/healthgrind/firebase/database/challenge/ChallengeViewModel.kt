package com.example.healthgrind.firebase.database.challenge

import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.database.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {

    var challenges = storageService.challenges

    // USE CASES
    // - alle kraft/run/walk/outdoor challenges geben
    // - ggf. noch filtern nach difficulty und gametype
    // - eine Challenge abschließen
    // - eine Challenge aktualisieren (Fortschritt)

    fun filterChallengesAndGet(exerciseType: String) {
        launchCatching {
            challenges = storageService.getSpecificChallenges(exerciseType)
        }
    }
}