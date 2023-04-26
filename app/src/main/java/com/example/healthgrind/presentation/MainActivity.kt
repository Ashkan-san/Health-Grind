package com.example.healthgrind.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.firebase.auth.register.SignUpScreen
import com.example.healthgrind.firebase.database.challenge.ChallengesScreen
import com.example.healthgrind.firebase.database.platform.PlatformsScreen
import com.example.healthgrind.firebase.database.reward.TestRewardScreen
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.presentation.screens.*
import com.example.healthgrind.presentation.screens.challenge.ExercisesScreen
import com.example.healthgrind.presentation.screens.input.ProfileInputScreen
import com.example.healthgrind.presentation.theme.HealthGrindTheme
import com.example.healthgrind.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dataSource: DataSource
    private lateinit var pref: SharedPreferences
    private lateinit var introPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SCREEN ANLASSEN ZUM DEBUGGEN
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            HealthGrindTheme {
                navController = rememberSwipeDismissableNavController()
                dataSource = DataSource()
                mainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                introPref = getSharedPreferences("introPref", MODE_PRIVATE)

                // FIRST START
                if (introPref.getBoolean("firstStart", true)) {
                    println("FIRST")
                    InitNavHost(true)
                } else {
                    println("NOT FIRST")
                    InitNavHost(false)
                }
            }
        }
    }

    @Composable
    fun InitNavHost(first: Boolean) {
        var startDestination = Screen.Start.route
        if (first) {
            startDestination = Screen.FirstStart.route
        }
        // NAVIGATION - NAVHOST
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            // 1. (FIRST) START SCREEN
            composable(Screen.Start.route) {
                StartScreen(navController = navController, pref = introPref)
            }
            composable(Screen.FirstStart.route) {
                FirstStartScreen(navController = navController)
            }
            composable(Screen.Debug.route) {
                DebugScreen(navController = navController)
            }

            // 2. SIGN UP
            composable(Screen.SignUp.route) {
                SignUpScreen(navController = navController)
            }

            // 3. PLAYER INFORMATION
            composable(Screen.PlayerInfo.route) {
                PlayerInfoScreen(navController = navController)
            }
            composable(Screen.NameInput.route) {
                NameInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.AgeInput.route) {
                AgeInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.GenderInput.route) {
                GenderInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.SkillInput.route) {
                SkillInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.WeightInput.route) {
                WeightInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.HeightInput.route) {
                HeightInputScreen(navController = navController, pref = introPref)
            }
            composable(Screen.ProfileInput.route) {
                ProfileInputScreen(navController = navController, pref = introPref)
            }

            // GAMES/EXERCISES/CHALLENGES/REWARDS
            composable(Screen.Platforms.route) {
                PlatformsScreen(navController = navController)
            }
            composable(
                "${Screen.Exercises.route}/{id}"
            ) {
                ExercisesScreen(
                    navController = navController,
                    id = it.arguments?.getString("id")
                )
            }
            composable(
                "${Screen.Challenges.route}/{id}/{id2}"
            ) {
                ChallengesScreen(
                    navController = navController,
                    platformId = it.arguments?.getString("id"),
                    exerciseId = it.arguments?.getString("id2"),
                    mainViewModel = mainViewModel
                )
            }
            composable("${Screen.RewardDialog.route}/{index}") {
                RewardScreen(
                    mainViewModel = mainViewModel,
                    navController = navController,
                    filChallIndex = it.arguments?.getString("index")
                )
            }
            composable("${Screen.Running.route}/{index}") {
                RunningScreen(
                    mainViewModel = mainViewModel,
                    filChallIndex = it.arguments?.getString("index"),
                    navController = navController,
                    dataSource = dataSource
                )
            }
            composable("${Screen.Walk.route}/{index}") {

            }
            composable("${Screen.Strength.route}/{index}") {
                StrengthScreen(
                    mainViewModel = mainViewModel,
                    filChallIndex = it.arguments?.getString("index"),
                    navController = navController,
                    dataSource = dataSource
                )
            }

            // TEST REWARDS
            composable(Screen.TestReward.route) {
                TestRewardScreen(navController = navController)
            }
        }
    }
}

