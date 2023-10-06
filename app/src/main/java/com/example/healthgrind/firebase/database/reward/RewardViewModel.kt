package com.example.healthgrind.firebase.database.reward

import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.general.HealthGrindViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RewardViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {
    val rewards = storageService.rewards

    // Wenn 5 erreicht dann Code in AllRewardsScreen unlocken und das selbe bei 10
    fun onUnlockReward(reward: Reward, index: Int, mandatoryCount: Int) {
        launchCatching {
            if (mandatoryCount >= 5 && index == 0) {
                storageService.updateReward(reward.copy(unlocked = true))
            }

            if (mandatoryCount >= 10 && index == 1) {
                storageService.updateReward(reward.copy(unlocked = true))
            }
        }
    }

    // Bei Click auf den Chip den Code anzeigen
    fun onRedeemReward(reward: Reward) {
        launchCatching {
            if (reward.unlocked) {
                storageService.updateReward(reward.copy(redeemed = true))
            }
        }
    }
}