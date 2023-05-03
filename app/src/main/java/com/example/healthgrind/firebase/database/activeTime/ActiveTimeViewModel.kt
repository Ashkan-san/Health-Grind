/*
package com.example.healthgrind.firebase.database.activeTime

import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.database.StorageService
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActiveTimeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {
    private var lastEventTime: Long = 0
    private var lastUpdateTime: Long = 0
    private var activeTime = mutableStateOf(0L)

    fun trackUsageTime() {
        launchCatching {
            while (true) {
                val stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, lastEventTime, System.currentTimeMillis())
                val eventTime = System.currentTimeMillis()
                for (stat in stats) {
                    if (stat.lastTimeUsed > lastEventTime) {
                        activeTime.value += (eventTime - stat.lastTimeUsed)
                    }
                }
                lastEventTime = eventTime

                // Save active time in Firestore once a day
                val calendar = Calendar.getInstance().apply { timeInMillis = lastUpdateTime }
                val today = Calendar.getInstance()
                if (calendar.get(Calendar.YEAR) != today.get(Calendar.YEAR) || calendar.get(Calendar.DAY_OF_YEAR) != today.get(Calendar.DAY_OF_YEAR)) {
                    storageService.updateCurrentUser("usageTime", activeTime.value)

                    lastUpdateTime = today.timeInMillis
                    activeTime.value = 0 // Reset active time for the next day
                }

                delay(1000) // wait for 1 second
            }
        }
    }
}
*/
