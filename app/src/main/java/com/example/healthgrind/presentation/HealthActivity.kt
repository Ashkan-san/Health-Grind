package com.example.healthgrind.presentation

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import com.example.healthgrind.presentation.theme.HealthGrindTheme
import com.example.healthgrind.viewmodel.MainViewModel


class HealthActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var mSensorManager: SensorManager
    private var mHeartRateSensor: Sensor? = null
    private var mStepCountSensor: Sensor? = null
    private var mAccelerationSensor: Sensor? = null
    private var mGyroSensor: Sensor? = null

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

        HealthGrindTheme {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item { Text(text = "Heart Rate: $heart") }
                item { Text(text = "Step Count: $stepCount") }
                item { Text(text = "Acceleration: $accelX, $accelY, $accelZ") }
                item { Text(text = "Gyro: $gyroX, $gyroY, $gyroZ") }
            }
        }
    }

    private fun setSensors() {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        mStepCountSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        mAccelerationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mGyroSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        println("onAccuracyChanged - accuracy: $accuracy")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (::mainViewModel.isInitialized) {
            if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
                val msg = "" + event.values[0].toInt()
                mainViewModel.setHeart(msg)

            } else if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                val msg = event.values[0].toInt()
                println("FLOAT:" + event.values[0])
                println("INT: " + msg)
                mainViewModel.setStepCount(msg)

            } else if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                val msg1 = "" + event.values[0].toInt()
                val msg2 = "" + event.values[1].toInt()
                val msg3 = "" + event.values[2].toInt()
                mainViewModel.setGyro(msg1, msg2, msg3)

            } else if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                val msg1 = "" + event.values[0].toInt()
                val msg2 = "" + event.values[1].toInt()
                val msg3 = "" + event.values[2].toInt()
                mainViewModel.setAccel(msg1, msg2, msg3)

            } else {
                println("Unknown Sensor Type")
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST)
        mSensorManager.registerListener(this, mStepCountSensor, SensorManager.SENSOR_DELAY_FASTEST)
        mSensorManager.registerListener(
            this,
            mAccelerationSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        mSensorManager.registerListener(this, mGyroSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

}