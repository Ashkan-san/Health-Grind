package com.example.healthgrind.firebase.database.reward

import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
) : HealthGrindViewModel(logService) {

    // TODO WAS BRAUCHEN WIR AN USE CASES?
    // Rewards redeemen
    // Rewards auflisten (nur die freigeschalteten bzw. code verstecken)
    val rewards = storageService.rewards

    fun onRewardRedeemed(reward: Reward) {
        launchCatching {
            storageService.updateReward(reward.copy(redeemed = true, unlocked_by = ""))
        }
    }
}