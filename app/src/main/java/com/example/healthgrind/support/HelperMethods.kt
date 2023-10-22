package com.example.healthgrind.support

import android.Manifest
import android.app.RemoteInput
import android.app.usage.UsageStatsManager
import android.content.ContentValues.TAG
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.healthgrind.R
import com.example.healthgrind.firebase.database.challenge.Challenge
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.Calendar
import java.util.concurrent.TimeUnit

fun getUsageTime(usageStatsManager: UsageStatsManager): Long {
    val cal = Calendar.getInstance()
    cal.add(Calendar.MONTH, -1)
    val queryUsageStats = usageStatsManager.queryAndAggregateUsageStats(
        cal.timeInMillis,
        System.currentTimeMillis()
    )
    val totalUsageTime = mutableStateOf(0L)

    for (e in queryUsageStats) {
        if (e.key == "com.example.healthgrind") {
            totalUsageTime.value = e.value.totalTimeInForeground / 60000
            break
        }
    }
    println("TIME USAGE IN MINUTES: ${totalUsageTime.value}")
    return totalUsageTime.value
}

@Composable
fun TextInput(
    value: String?,
    icon: Int,
    isPassword: Boolean = false,
    onInputChange: (value: String) -> Unit
) {
    var inputString by remember { mutableStateOf("") }

    // TEXT INPUT LAUNCHER ZEUG
    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newValue: CharSequence? = results.getCharSequence(value)

                // DER GEWOLLTE STRING
                inputString = newValue.toString()

                onInputChange(inputString)
            }
        }

    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 10.dp),
        label = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colors.onPrimary,
                text = value!!
            )
        },
        secondaryLabel = {
            Text(
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                text = if (!isPassword) inputString else "*".repeat(inputString.length)
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        },
        // Tastatur öffnen
        onClick = {
            val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent();
            val remoteInputs: List<RemoteInput> = listOf(
                RemoteInput.Builder(value!!)
                    .setLabel(value)
                    .wearableExtender {
                        setEmojisAllowed(false)
                        setInputActionType(EditorInfo.IME_ACTION_DONE)
                    }.build()
            )

            RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

            launcher.launch(intent)
        }
    )
}

@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions() {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.ACTIVITY_RECOGNITION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            //Manifest.permission.PACKAGE_USAGE_STATS
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }

                else -> {
                    // TODO
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })

    /*ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = rememberScalingLazyListState(initialCenterItemIndex = 0)
    )
    {
        permissionStates.permissions.forEach {
            when (it.permission) {
                Manifest.permission.BODY_SENSORS -> {
                    when {
                        it.status.isGranted -> {
                            item { Text(text = "Body Sensor: GRANTED") }
                        }
                        it.status.shouldShowRationale -> {
                            item { Text(text = "Body Sensor: NEEDED") }
                        }
                        !it.status.isGranted && !it.status.shouldShowRationale -> {
                            item { Text(text = "Navigate to settings and enable the Body Sensor Permission") }

                        }
                    }
                }

                Manifest.permission.ACTIVITY_RECOGNITION -> {
                    when {
                        it.status.isGranted -> {
                            item { Text(text = "Physical Activity: GRANTED") }
                        }
                        it.status.shouldShowRationale -> {
                            item { Text(text = "Physical Activity: NEEDED") }
                        }
                        !it.status.isGranted && !it.status.shouldShowRationale -> {
                            item { Text(text = "Navigate to settings and enable the Physical Activity Permission") }

                        }
                    }
                }

                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when {
                        it.status.isGranted -> {
                            item { Text(text = "Location: GRANTED") }
                        }
                        it.status.shouldShowRationale -> {
                            item { Text(text = "Location: NEEDED") }
                        }
                        !it.status.isGranted && !it.status.shouldShowRationale -> {
                            item { Text(text = "Navigate to settings and enable the Location Permission") }

                        }
                    }
                }
            }
        }
    }*/
}

@Composable
fun CountDownButton(
    isPlaying: Boolean,
    optionSelected: () -> Unit
) {
    Button(
        onClick = { optionSelected() },
        modifier = Modifier.size(50.dp)
    ) {
        Icon(
            painter = painterResource(id = if (!isPlaying) R.drawable.ic_play else R.drawable.ic_pause),
            contentDescription = "Play/Pause Button",
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colors.primaryVariant)
        )
    }
}

@Composable
fun SensorStuff(sensorManager: SensorManager, viewModel: ChallengeViewModel) {
    val heartRateSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE) }
    val stepDetectorSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) }
    val stepCounterSensor = remember { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }

    val challenge = viewModel.currentChallengeState

    DisposableEffect(Unit) {
        val sensorListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d(TAG, "ACCURACY CHANGED: $accuracy")
            }

            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.type) {
                    Sensor.TYPE_HEART_RATE -> {
                        val heartRate = event.values[0].toInt()
                        viewModel.setHeartRate(heartRate)
                        Log.d(TAG, "HEART RATE CHANGED: $heartRate")
                    }

                    Sensor.TYPE_STEP_DETECTOR -> {
                        viewModel.increaseStepCount()

                        // NUR UPDATEN WENN WALK SCREEN
                        if (challenge.value.exerciseType == ExerciseType.WALK) {
                            viewModel.updateCurrent(challenge.value, viewModel.stepCount.value)
                        }
                        Log.d(
                            TAG, "STEP COUNT CHANGED: STEPS=${viewModel.stepCount.value}, " +
                                    "METER=${viewModel.stepsInMeter.value}"
                        )
                    }

                    Sensor.TYPE_STEP_COUNTER -> {
                        val allSteps = event.values[0].toInt()
                        viewModel.updateAllSteps(allSteps)
                    }

                    else -> Log.d(TAG, "SENSORS NOT FOUND")
                }
            }
        }

        sensorManager.registerListener(
            sensorListener,
            heartRateSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        sensorManager.registerListener(
            sensorListener,
            stepDetectorSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        sensorManager.registerListener(
            sensorListener,
            stepCounterSensor,
            SensorManager.SENSOR_DELAY_FASTEST
        )
        Log.d(TAG, "SENSORS REGISTERED: $heartRateSensor, $stepDetectorSensor, $stepCounterSensor")

        onDispose {
            viewModel.handleCountDownTimer(true)
            sensorManager.unregisterListener(sensorListener)
        }
    }
}

@Composable
fun PlusAndFinishButtons(challenge: Challenge, viewModel: ChallengeViewModel, vibrator: Vibrator) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Button(
            onClick = {
                viewModel.updateCurrent(challenge, challenge.current + 1)
            }, modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = if (challenge.current != challenge.goal) R.drawable.ic_plus else R.drawable.check),
                contentDescription = "Plus",
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
        // Button, um Challenge zu finishen (statt 50 Mal den Knopf drücken zu müssen)
        Button(
            onClick = {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                viewModel.finishChallenge(challenge)
            }, modifier = Modifier.size(40.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.goal),
                contentDescription = "Finish Challenge",
                modifier = Modifier
                    .size(40.dp)
                    .background(MaterialTheme.colors.primaryVariant)
            )
        }
    }
}

@Composable
fun StepsInMeterIndicator(stepsInMeter: Float) {
    Text(
        text = "${stepsInMeter.toInt()}" + "m",
        color = MaterialTheme.colors.onPrimary,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
}

@Composable
fun HeartRateIndicator(heartRate: Int) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Image(
            painter = painterResource(R.drawable.heart),
            contentDescription = "Heart Rate",
            modifier = Modifier,
            colorFilter = ColorFilter.tint(MaterialTheme.colors.secondaryVariant)
        )
        Text(
            text = heartRate.toString(),
            color = MaterialTheme.colors.onPrimary,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun AutoResizingText(text: String, color: Color = MaterialTheme.colors.onPrimary) {
    val textStyleBody2 = MaterialTheme.typography.body2
    var textStyleNew by remember { mutableStateOf(textStyleBody2) }
    var readyToDraw by remember { mutableStateOf(false) }

    Text(
        text = text,
        color = color,
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

@Composable
fun FullScreenProgressIndicator(
    progress: Float
) {
    // PROGRESS INDICATOR
    CircularProgressIndicator(
        // 270° ist ganz oben, 45° in beide Richtungen für Platz
        startAngle = 300f,
        endAngle = 240f,
        trackColor = MaterialTheme.colors.primary,
        indicatorColor = MaterialTheme.colors.primaryVariant,
        strokeWidth = 7.dp,
        // Progress von 0.0 bis 1.0
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
    )
}

fun myFormatTime(value: Long): String = String.format(
    "%02d:%02d",
    TimeUnit.MILLISECONDS.toMinutes(value),
    TimeUnit.MILLISECONDS.toSeconds(value) % 60
)

@Composable
fun LoadImageFromStorage(imagePath: String, modifier: Modifier) {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(imagePath).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        Log.d(TAG, "BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(link)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCacheKey(imagePath)
            .memoryCacheKey(imagePath)
            .build(),
        contentDescription = "Image",
        modifier = modifier
    )
}

@Composable
fun loadPlatformImageFromStorage(imagePath: String): String {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(imagePath).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        Log.d(TAG, "BILD IST AN DIESER ADRESSE NICHT VORHANDEN.")
    }

    return link
}

fun createOptionalChallengesForUser(): List<Challenge> {
    return listOf(
        Challenge(
            title = "Schritte",
            mandatory = false,
            exerciseType = ExerciseType.WALK,
            goal = 5000,
        ),
        Challenge(
            title = "Schritte",
            mandatory = false,
            exerciseType = ExerciseType.WALK,
            goal = 5000,
        ),
        Challenge(
            title = "Laufen",
            mandatory = false,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 30,
        ),
        Challenge(
            title = "Laufen",
            mandatory = false,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 30,
        ),
        Challenge(
            title = "Handstand",
            mandatory = false,
            exerciseType = ExerciseType.STRENGTH,
            goal = 1,
        ),
        Challenge(
            title = "Kopfstand",
            mandatory = false,
            exerciseType = ExerciseType.STRENGTH,
            goal = 1,
        ),
        Challenge(
            title = "Radschlag",
            mandatory = false,
            exerciseType = ExerciseType.STRENGTH,
            goal = 1,
        ),
        Challenge(
            title = "Muscle Up",
            mandatory = false,
            exerciseType = ExerciseType.STRENGTH,
            goal = 1,
        ),
        Challenge(
            title = "Spagat",
            mandatory = false,
            exerciseType = ExerciseType.STRENGTH,
            goal = 1,
        ),
    )
}

fun createAllChallengesForUser(): List<Challenge> {
    return listOf(
        Challenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 8000,
        ),
        Challenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 8000,
        ),
        Challenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 4000,
        ),
        Challenge(
            title = "Schritte",
            mandatory = true,
            exerciseType = ExerciseType.WALK,
            goal = 4000,
        ),
        Challenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 35,
        ),
        Challenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 35,
        ),
        Challenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 35,
        ),
        Challenge(
            title = "Laufen",
            mandatory = true,
            exerciseType = ExerciseType.RUN,
            goal = 60000 * 35,
        ),
        Challenge(
            title = "Liegestütze",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
        Challenge(
            title = "Kniebeuge",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
        Challenge(
            title = "Klimmzüge",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
        Challenge(
            title = "Sit Ups",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        ),
        Challenge(
            title = "Dips",
            mandatory = true,
            exerciseType = ExerciseType.STRENGTH,
            goal = 50,
        )
    )
}