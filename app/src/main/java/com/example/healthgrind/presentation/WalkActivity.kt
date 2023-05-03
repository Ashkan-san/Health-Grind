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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.data.FullScreenProgressIndicator
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.presentation.screens.MainViewModel
import com.example.healthgrind.presentation.theme.HealthGrindTheme


class WalkActivity : ComponentActivity(), SensorEventListener {
    private lateinit var mainViewModel: MainViewModel
    //private lateinit var navController: NavHostController
    //private lateinit var dataSource: DataSource

    private lateinit var mSensorManager: SensorManager
    private var mHeartRateSensor: Sensor? = null
    private var mStepCountSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setSensors()

        setContent {
            mainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
            WalkScreen(
                mainViewModel = mainViewModel,
                filChallIndex = intent.getStringExtra("index")
            )


        }
    }

    private fun setSensors() {
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        mStepCountSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
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
                println("FLOAT2:" + event.values[0])
                println("INT2: " + msg)
                mainViewModel.setStepCount(msg)
            } else {
                println("Unknown sensor")
            }
        }

        /*if (mainViewModel.stepsFinished()) {
            val index = intent.getStringExtra("index")
            mainViewModel.setWeiter(false)
            navController.navigate("${Screen.RewardDialog.route}/${index}")
        }*/

    }

    override fun onResume() {
        super.onResume()
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_FASTEST)
        mSensorManager.registerListener(this, mStepCountSensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager.unregisterListener(this)
    }

    @Composable
    fun WalkScreen(
        mainViewModel: MainViewModel, filChallIndex: String?, //dataSource: DataSource
    ) {
        // TODO ändern wieder
        var filteredChallenges = emptyArray<Challenge>()
        val challenge: Challenge = filteredChallenges[filChallIndex!!.toInt()]

        val steps by mainViewModel.stepCount.observeAsState(0)
        val goal by mainViewModel.stepGoal.observeAsState(0)
        mainViewModel.setStepGoal(challenge.goal)
        val progress by mainViewModel.strengthProgress.observeAsState(0.00F)
        val heartRate by mainViewModel.heart.observeAsState()

        val finish by challenge.finished.observeAsState(false)
        val weiter by mainViewModel.weiter.observeAsState(false)

        HealthGrindTheme {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
                userScrollEnabled = false

            ) {
                // TITLE
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primaryVariant,
                        text = "Challenge",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.onPrimary,
                        text = "${challenge.goal} ${challenge.title}",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }
                item {
                    StepsIndicator(steps = steps, goal = goal)
                }
                item {
                    HeartRateIndicator(hr = heartRate!!)
                }
            }

            FullScreenProgressIndicator(progress = progress)

            //disableChallengeChip(weiter, dataSource, filChallIndex, mainViewModel, navController)
        }
    }

    @Composable
    fun HeartRateIndicator(hr: String) {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            userScrollEnabled = false
        ) {
            item {
                Image(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "Heart Rate",
                    modifier = Modifier//.size(10.dp)
                )
            }
            item {
                Text(
                    text = hr,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }
    }

    @Composable
    fun StepsIndicator(steps: Int, goal: Int) {
        Box(
            modifier = Modifier
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            AutoResizingText(text = "${steps}/${goal}")
        }
    }

}

@Composable
fun AutoResizingText(text: String) {
    val textStyleBody2 = MaterialTheme.typography.body2
    var textStyleNew by remember { mutableStateOf(textStyleBody2) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = MaterialTheme.colors.onPrimary,
        textAlign = TextAlign.Center,
        maxLines = 1,
        style = textStyleNew,
        modifier = Modifier.drawWithContent {
            if (readyToDraw) drawContent()
        },
        onTextLayout = { textLayoutResult ->
            if (textLayoutResult.hasVisualOverflow) {
                textStyleNew = textStyleNew.copy(fontSize = textStyleNew.fontSize * 0.8)
            } else {
                readyToDraw = true
            }
        }
    )
}
