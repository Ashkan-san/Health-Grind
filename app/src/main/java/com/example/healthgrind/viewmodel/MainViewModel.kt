package com.example.healthgrind.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


/**
 * KLASSE FÜR DATEN DER UI
 */
class MainViewModel() : ViewModel() {
    // -------------- DATEN -----------------

    private val _accelX = MutableLiveData("0")
    val accelX: LiveData<String> = _accelX
    private val _accelY = MutableLiveData("0")
    val accelY: LiveData<String> = _accelY
    private val _accelZ = MutableLiveData("0")
    val accelZ: LiveData<String> = _accelZ

    private val _heart = MutableLiveData("0")
    val heart: LiveData<String> = _heart

    private val _stepCount = MutableLiveData(0)
    val stepCount: LiveData<Int> = _stepCount
    private val _stepGoal = MutableLiveData(0)
    val stepGoal: LiveData<Int> = _stepGoal

    private val _gyroX = MutableLiveData("0")
    val gyroX: LiveData<String> = _gyroX
    private val _gyroY = MutableLiveData("0")
    val gyroY: LiveData<String> = _gyroY
    private val _gyroZ = MutableLiveData("0")
    val gyroZ: LiveData<String> = _gyroZ

    private val _reps = MutableLiveData(0F)
    val reps: LiveData<Float> = _reps
    private val _strengthGoal = MutableLiveData(0F)
    val strengthGoal: LiveData<Float> = _strengthGoal
    private val _strengthProgress = MutableLiveData(0.00F)
    val strengthProgress: LiveData<Float> = _strengthProgress

    private var countDownTimer: CountDownTimer? = null

    private val _timeGoal = MutableLiveData(0L)
    val timeGoal: LiveData<Long> = _timeGoal

    private val _time = MutableLiveData(0L)
    val time: LiveData<Long> = _time


    private val _progress = MutableLiveData(1.00F)
    val progress: LiveData<Float> = _progress
    private val _isPlaying = MutableLiveData(false)
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val _weiter = MutableLiveData(false)
    val weiter: LiveData<Boolean> = _weiter

    private val _showDialog = MutableLiveData(false)
    val showDialog: LiveData<Boolean> = _showDialog

    // --------------- METHODEN ------------------
    fun setAccel(msg1: String, msg2: String, msg3: String) {
        _accelX.value = msg1
        _accelY.value = msg2
        _accelZ.value = msg3
    }

    fun setHeart(msg: String) {
        _heart.value = msg
    }

    fun setStepCount(msg: Int) {
        _stepCount.value = msg
    }

    fun setStepGoal(value: Int) {
        _stepGoal.value = value
    }

    fun setGyro(msg1: String, msg2: String, msg3: String) {
        _gyroX.value = msg1
        _gyroY.value = msg2
        _gyroZ.value = msg3
    }

    fun setTimeGoal(value: Long) {
        _timeGoal.value = value
        _time.value = value
    }

    fun handleCountDownTimer() {
        if (isPlaying.value == true) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        updateTimerValues(false, time.value!!, progress.value!!)
    }

    private fun startTimer() {
        _isPlaying.value = true
        // SO SETZT SICH DER TIMER BEIM START ZURÜCK
        //

        countDownTimer = object : CountDownTimer(_time.value!!, 1000) {

            override fun onTick(millisRemaining: Long) {
                val progressValue = millisRemaining.toFloat() / timeGoal.value!!
                updateTimerValues(true, millisRemaining, progressValue)
            }

            override fun onFinish() {
                pauseTimer()
                _weiter.value = true
            }
        }.start()
    }

    private fun updateTimerValues(isPlaying: Boolean, time: Long, progress: Float) {
        _isPlaying.value = isPlaying
        _time.value = time
        _progress.value = progress
    }

    fun stepsFinished(): Boolean {
        if (stepCount.value!! >= stepGoal.value!!) {
            _weiter.value = true
            _stepCount.value = 0
        }
        return weiter.value!!
    }

    fun setStrengthGoal(value: Float) {
        _strengthGoal.value = value
    }

    fun increaseReps() {
        if (reps.value!! < (strengthGoal.value!!)) {
            // ADD 1
            _reps.value = reps.value?.plus(1)

            // Progress aktualisieren
            if (reps.value != 0F) {
                val progress = reps.value!!.div(strengthGoal.value!!)
                _strengthProgress.value = progress
            }
        } else {
            resetStrength()
            _weiter.value = true
        }
    }

    fun resetStrength() {
        _reps.value = 0F
        _strengthProgress.value = 0F
    }

    fun setWeiter(b: Boolean) {
        _weiter.value = b
    }

    fun updateDialog(bool: Boolean) {
        _showDialog.value = bool
    }

}


/*
class MainViewModelFactory(val pref: SharedPreferences) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(pref) as T
}
*/
