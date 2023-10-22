package com.example.healthgrind.firebase.database.challenge

import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.general.HealthGrindViewModel
import com.example.healthgrind.support.ExerciseType
import com.example.healthgrind.support.getUsageTime
import com.google.firebase.Timestamp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {

    var challenges: Flow<List<Challenge>>? = null
    val currentChallengeState = mutableStateOf(Challenge())

    val statisticsState = mutableStateOf(Statistics())
    val allSteps = mutableStateOf(0)
    val usageTime = mutableStateOf(0L)

    var countDownTimer: CountDownTimer? = null
    var isPlaying = mutableStateOf(false)
    private var currentTimerValue = mutableStateOf(0L)

    val heartRate = mutableStateOf(0)
    val stepCount = mutableStateOf(0)
    val stepsInMeter = mutableStateOf(0F)
    private val strideLength = 0.5F

    fun setHeartRate(value: Int) {
        heartRate.value = value
    }

    fun increaseStepCount() {
        stepCount.value += 1
        stepsInMeter.value = stepCount.value * strideLength
    }

    // DIE AUSGEWÄHLTE CHALLENGE SETZEN
    fun setCurrentChallenge(challenge: Challenge) {
        currentChallengeState.value = challenge

        if (challenge.exerciseType == ExerciseType.RUN) {
            // Am Anfang Current auf Goal setzen, um dann runterzuzählen
            if (challenge.current == 0) currentTimerValue.value =
                challenge.goal.toLong() else currentTimerValue.value = challenge.current.toLong()
        }
    }

    // Nach ExerciseType usw. filtern
    fun filterChallengesAndSet(exerciseType: String) {
        launchCatching {
            challenges = storageService.getSpecificChallenges(exerciseType)
        }
    }

    // Das Ziel von 5 auf 10 erhöhen, sobald der Count 5 erreicht
    fun updateMandatoryGoal() {
        if (statisticsState.value.mandatoryCount >= 5) {
            statisticsState.value.mandatoryGoal = 10
        }
    }

    // CURRENT VON CHALLENGE ERHÖHEN
    fun updateCurrent(challenge: Challenge, value: Int) {
        if (challenge.current < (challenge.goal)) {
            // CURRENT AKTUALISIEREN
            // PROGRESS FÜR INDIKATOR AKTUALISIEREN
            currentChallengeState.value = challenge.copy(
                current = value,
                progress = value.toFloat().div(challenge.goal.toFloat()),
                timeStampStart =
                if (challenge.timeStampStart.toDate() == Date(123, 1, 1))
                    Timestamp.now() else challenge.timeStampStart
            )
            // UPDATE CHALLENGE IN DATABASE
            launchCatching { storageService.updateChallenge(currentChallengeState.value) }
        } else {
            // Challenge auf finished setzen
            finishChallenge(challenge)
        }
    }

    // Challenge direkt finishen, um repetitives Knopfdrücken zu vermeiden
    fun finishChallenge(challenge: Challenge) {
        Log.d(TAG, "CHALLENGE FINISHED!")
        currentChallengeState.value =
            challenge.copy(
                current = challenge.goal,
                finished = true,
                progress = 1.0F,
                timeStampFinish = Timestamp.now()
            )
        if (challenge.mandatory) statisticsState.value.mandatoryCount += 1 else statisticsState.value.optionalCount += 1

        statisticsState.value.completedSteps = allSteps.value
        statisticsState.value.usageTime = usageTime.value

        launchCatching {
            storageService.updateChallenge(currentChallengeState.value)
            storageService.updateStats(statisticsState.value)
        }
    }

    fun updateUsageTime(usageStatsManager: UsageStatsManager) {
        usageTime.value = getUsageTime(usageStatsManager)
    }

    fun updateAllSteps(value: Int) {
        /* if (value < statisticsState.value.completedSteps) {
             allSteps.value += value
         } else {
             allSteps.value = value
         }*/
        allSteps.value = value
    }

    // TIMER ZEUG
    fun handleCountDownTimer(boolean: Boolean = isPlaying.value) {
        if (boolean) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        Log.d(TAG, "TIMER STARTED")
        isPlaying.value = true
        val challenge = currentChallengeState.value

        countDownTimer = object : CountDownTimer(currentTimerValue.value, 1000) {
            override fun onTick(millisRemaining: Long) {
                Log.d(TAG, "TICK: $millisRemaining")
                currentTimerValue.value = millisRemaining
                updateCurrent(challenge, millisRemaining.toInt())
            }

            override fun onFinish() {
                Log.d(TAG, "TIMER FINISHED")
                pauseTimer()
                finishChallenge(challenge)
            }
        }.start()
    }

    private fun pauseTimer() {
        Log.d(TAG, "TIMER PAUSED")
        countDownTimer?.cancel()
        isPlaying.value = false
    }

    fun setStepsFromChallenge() {
        stepCount.value = currentChallengeState.value.current
        stepsInMeter.value = currentChallengeState.value.current * strideLength
    }

    fun getStatisticsFromFirestore() {
        launchCatching {
            statisticsState.value = storageService.getStatistics()!!
            //allSteps.value = statisticsState.value.completedSteps
        }
    }
}