package com.example.healthgrind.presentation

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import com.example.healthgrind.presentation.screens.MainViewModel
import com.example.healthgrind.presentation.theme.HealthGrindTheme


class HealthActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var sensorManager: SensorManager
    private var heartRateSensor: Sensor? = null
    private var stepCountSensor: Sensor? = null
    private var accelerationSensor: Sensor? = null
    private var gyroSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setSensors()

        // TODO Berechtigungen müssen noch per Hand an der Watch gegeben werden, ändern?
        setContent {
            mainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            HealthScreen()
        }
    }

    private fun setSensors() {
        Log.d(TAG, "SENSORS SET")
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        accelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    @Composable
    fun HealthScreen() {
        val accelX by mainViewModel.accelX.observeAsState("")
        val accelY by mainViewModel.accelY.observeAsState("")
        val accelZ by mainViewModel.accelZ.observeAsState("")
        val heart by mainViewModel.heart.observeAsState("")
        val stepCount by mainViewModel.stepCount.observeAsState("")
        val gyroX by mainViewModel.gyroX.observeAsState("")
        val gyroY by mainViewModel.gyroY.observeAsState("")
        val gyroZ by mainViewModel.gyroZ.observeAsState("")

        val pushUp by mainViewModel.pushUp.observeAsState()

        HealthGrindTheme {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = rememberScalingLazyListState(),
                userScrollEnabled = false
            ) {
                item { Text(text = "Push Ups: $pushUp", color = Color.White) }

                item { Text(text = "Heart Rate: $heart", color = Color.White) }
                item { Text(text = "Step Count: $stepCount", color = Color.White) }
                item { Text(text = "Acceleration: $accelX, $accelY, $accelZ", color = Color.White) }
                item { Text(text = "Gyro: $gyroX, $gyroY, $gyroZ", color = Color.White) }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d(TAG, "ACCURACY CHANGED: $accuracy")
    }

    /*// Constants for push-up detection
    private val PUSH_UP_THRESHOLD = 8f
    private val MIN_PUSH_UP_TIME_NS = 500000000 // 500ms

    // Variables for push-up detection
    private var lastPushUpTime: Long = 0
    private var pushUpCount = 0
    private var wasPushingUp = false*/

    override fun onSensorChanged(event: SensorEvent) {
        if (::mainViewModel.isInitialized) {
            when (event.sensor.type) {
                Sensor.TYPE_HEART_RATE -> {
                    val msg = "" + event.values[0].toInt()
                    mainViewModel.setHeart(msg)
                }
                Sensor.TYPE_STEP_COUNTER -> {
                    val msg = event.values[0].toInt()
                    println("FLOAT:" + event.values[0])
                    println("INT: " + msg)
                    mainViewModel.setStepCount(msg)
                }
                Sensor.TYPE_GYROSCOPE -> {
                    val msg1 = "" + event.values[0].toInt()
                    val msg2 = "" + event.values[1].toInt()
                    val msg3 = "" + event.values[2].toInt()
                    mainViewModel.setGyro(msg1, msg2, msg3)
                }
                Sensor.TYPE_ACCELEROMETER -> {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    /*// Calculate the magnitude of the acceleration vector
                    val acceleration = sqrt(x * x + y * y + z * z)

                    // Check if push-up was performed
                    if (acceleration > PUSH_UP_THRESHOLD) {
                        val currentTime = System.nanoTime()

                        if (!wasPushingUp) {
                            // Start of push-up detected
                            wasPushingUp = true
                            lastPushUpTime = currentTime
                        } else {
                            // Push-up already started
                            val pushUpDuration = currentTime - lastPushUpTime
                            if (pushUpDuration > MIN_PUSH_UP_TIME_NS) {
                                // End of push-up detected
                                pushUpCount++
                                mainViewModel.setPushUpCount(pushUpCount)
                                wasPushingUp = false
                            }
                        }
                    }*/
                }
                else -> Log.d(TAG, "SENSORS NOT FOUND")
            }
        }
    }

    override fun onResume() {
        Log.d(TAG, "SENSORS REGISTERED")
        super.onResume()
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST)
        sensorManager.registerListener(
            this,
            accelerationSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        sensorManager.registerListener(this, gyroSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        Log.d(TAG, "SENSORS PAUSED")
        super.onPause()
        sensorManager.unregisterListener(this)
    }

}