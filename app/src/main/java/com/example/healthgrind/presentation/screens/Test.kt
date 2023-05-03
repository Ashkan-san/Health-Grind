package com.example.healthgrind.presentation.screens

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.abs

private const val PUSH_UPS_GOAL = 20
private const val DIRECTION_NONE = 0
private const val DIRECTION_UP = 1
private const val DIRECTION_DOWN = 2

@Composable
fun PushUpCounter() {
    var pushUpCount by remember { mutableStateOf(0) }

    lateinit var sensorManager: SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    val eventListener = remember { PushUpEventListener() }

    LaunchedEffect(accelerometer) {
        sensorManager.registerListener(
            eventListener,
            accelerometer,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Push-ups: $pushUpCount / $PUSH_UPS_GOAL")
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                pushUpCount++
            }
        ) {
            Text(text = "Add Push-up")
        }
    }
}

class PushUpEventListener : SensorEventListener {
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private var lastDirection = 0

    private var isPushUpStarted = false
    private var isPushUpCompleted = false

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0]
            val y = it.values[1]
            val z = it.values[2]

            val acceleration = abs(x + y + z - lastX - lastY - lastZ) / 3

            lastX = x
            lastY = y
            lastZ = z

            val direction = getDirection(acceleration)

            if (direction == DIRECTION_UP) {
                isPushUpStarted = true
            } else if (direction == DIRECTION_DOWN && isPushUpStarted) {
                isPushUpCompleted = true
                isPushUpStarted = false
            }

            if (isPushUpCompleted) {
                isPushUpCompleted = false
                CoroutineScope(Dispatchers.Default).launch {
                    incrementPushUpCount()
                }
            }

            lastDirection = direction
        }
    }

    private suspend fun incrementPushUpCount() {
        withContext(Dispatchers.Main) {
            // Increment push-up count in the UI thread
            // You can also update the count in a database or cloud storage
            // using a background thread
            // ...
        }
    }

    private fun getDirection(acceleration: Float): Int {
        return when {
            acceleration > 10 -> DIRECTION_UP
            acceleration < 5 -> DIRECTION_DOWN
            else -> lastDirection
        }
    }
}

