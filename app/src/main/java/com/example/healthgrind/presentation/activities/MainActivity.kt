package com.example.healthgrind.presentation.activities

import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.os.Vibrator
import android.provider.Settings
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.healthgrind.firebase.auth.register.SignUpScreen
import com.example.healthgrind.firebase.database.challenge.*
import com.example.healthgrind.firebase.database.platform.PlatformsScreen
import com.example.healthgrind.firebase.database.reward.AllRewardsScreen
import com.example.healthgrind.firebase.database.reward.RewardScreen
import com.example.healthgrind.presentation.screens.*
import com.example.healthgrind.presentation.screens.input.*
import com.example.healthgrind.presentation.theme.HealthGrindTheme
import com.example.healthgrind.support.MultiplePermissions
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.getUsageTime
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var pref: SharedPreferences
    private lateinit var sensorManager: SensorManager
    private lateinit var usageStatsManager: UsageStatsManager
    private lateinit var vibrator: Vibrator
    private lateinit var challengeViewModel: ChallengeViewModel

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            HealthGrindTheme {
                usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
                vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                navController = rememberSwipeDismissableNavController()
                pref = getSharedPreferences("pref", MODE_PRIVATE)
                challengeViewModel = hiltViewModel()

                //StatsPermission()
                MultiplePermissions()

                // INTRODUCTION WENN FIRST START
                if (pref.getBoolean("firstStart", true)) {
                    InitNavHost(true)
                } else {
                    InitNavHost(false)
                }
            }
        }
    }

    private fun StatsPermission() {
        val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), packageName)
        val granted = mode == AppOpsManager.MODE_ALLOWED

        //if (!granted) {
            Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                //this.setData(Uri.fromParts("com.example.healthgrind", packageName, null))
                startActivity(this)
            }
        //}
    }

    @Composable
    fun InitNavHost(first: Boolean) {

        val startDestination: String = if (first) {
            Screen.Intro.route
        } else {
            Screen.Start.route
        }

        // NAVIGATION - NAVHOST
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // 1. INTRO UND START SCREEN
            composable(Screen.Start.route) {
                StartScreen(
                    navController = navController,
                    pref = pref,
                    challengeViewModel,
                    vibrator = vibrator,
                    usageStatsManager
                )
            }
            composable(Screen.Intro.route) {
                IntroductionScreen(navController = navController, vibrator = vibrator)
            }
            composable(Screen.Debug.route) {
                DebugScreen(navController = navController)
            }
            composable(Screen.AllRewards.route) {
                AllRewardsScreen(
                    navController = navController,
                    viewModel = challengeViewModel,
                    vibrator = vibrator
                )
            }

            // 2. SIGN UP
            composable(Screen.SignUp.route) {
                SignUpScreen(navController = navController, vibrator = vibrator)
            }

            // 3. PLAYER INFORMATION
            composable(Screen.PlayerInfo.route) {
                PlayerInfoScreen(navController = navController)
            }
            composable(Screen.ProfileInput.route) {
                ProfileInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.NameInput.route) {
                NameInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.AgeInput.route) {
                AgeInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.HeightInput.route) {
                HeightInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.WeightInput.route) {
                WeightInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.GenderInput.route) {
                GenderInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }
            composable(Screen.SkillInput.route) {
                SkillInputScreen(navController = navController, pref = pref, vibrator = vibrator)
            }

            // 4. PLATFORMS/EXERCISES/CHALLENGES/REWARDS
            composable(Screen.Platforms.route) {
                PlatformsScreen(navController = navController, vibrator = vibrator)
            }
            composable(
                "${Screen.Exercises.route}/{id}"
            ) {
                ExercisesScreen(
                    navController = navController,
                    id = it.arguments?.getString("id"),
                    vibrator = vibrator
                )
            }
            composable(
                route = "${Screen.Challenges.route}/{id}/{id2}"
            ) { backStackEntry ->
                ChallengesScreen(
                    navController = navController,
                    platformId = backStackEntry.arguments?.getString("id"),
                    exerciseId = backStackEntry.arguments?.getString("id2"),
                    viewModel = challengeViewModel,
                    vibrator = vibrator
                )
            }
            composable(Screen.Strength.route) {
                StrengthScreen(
                    navController = navController,
                    sensorManager = sensorManager,
                    viewModel = challengeViewModel,
                    vibrator = vibrator
                )
            }
            composable(Screen.Walk.route) {
                WalkScreen(
                    viewModel = challengeViewModel,
                    sensorManager = sensorManager,
                    navController = navController
                )
            }

            composable(Screen.Run.route) {
                RunScreen(
                    viewModel = challengeViewModel,
                    sensorManager = sensorManager,
                    navController = navController,
                    vibrator = vibrator
                )
            }

            composable(Screen.Reward.route) {
                RewardScreen(
                    navController = navController,
                    viewModel = challengeViewModel,
                    vibrator = vibrator
                )
            }
        }
    }

    /*@Composable
    fun HealthGrindButton(button: Button) {
        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
        button
    }*/
}

