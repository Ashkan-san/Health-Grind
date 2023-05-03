package com.example.healthgrind.presentation

import android.content.SharedPreferences
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.example.healthgrind.firebase.auth.register.SignUpScreen
import com.example.healthgrind.firebase.database.challenge.ChallengeViewModel
import com.example.healthgrind.firebase.database.challenge.ChallengesScreen
import com.example.healthgrind.firebase.database.platform.PlatformsScreen
import com.example.healthgrind.firebase.database.reward.AllRewardsScreen
import com.example.healthgrind.presentation.screens.*
import com.example.healthgrind.presentation.screens.input.ProfileInputScreen
import com.example.healthgrind.presentation.theme.HealthGrindTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref: SharedPreferences

    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SCREEN ANLASSEN ZUM DEBUGGEN
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            HealthGrindTheme {
                navController = rememberSwipeDismissableNavController()
                mainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
                pref = getSharedPreferences("pref", MODE_PRIVATE)
                challengeViewModel = hiltViewModel()

                // FIRST START
                if (pref.getBoolean("firstStart", true)) {
                    InitNavHost(true)
                } else {
                    InitNavHost(false)
                }


            }
        }
    }

    @Composable
    fun InitNavHost(first: Boolean) {
        val startDestination: String

        // TODO so ändern dass es erst nach account erstellung gesetzt wird und so

        if (first) {
            startDestination = Screen.FirstStart.route
        } else {
            startDestination = Screen.Start.route
        }

        // NAVIGATION - NAVHOST
        SwipeDismissableNavHost(
            navController = navController,
            startDestination = startDestination
        ) {

            // 1. (FIRST) START SCREEN
            composable(Screen.Start.route) {
                StartScreen(navController = navController, pref = pref)
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
                NameInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.AgeInput.route) {
                AgeInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.GenderInput.route) {
                GenderInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.SkillInput.route) {
                SkillInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.WeightInput.route) {
                WeightInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.HeightInput.route) {
                HeightInputScreen(navController = navController, pref = pref)
            }
            composable(Screen.ProfileInput.route) {
                ProfileInputScreen(navController = navController, pref = pref)
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
                route = "${Screen.Challenges.route}/{id}/{id2}"
            ) { backStackEntry ->
                ChallengesScreen(
                    navController = navController,
                    platformId = backStackEntry.arguments?.getString("id"),
                    exerciseId = backStackEntry.arguments?.getString("id2"),
                    viewModel = challengeViewModel
                )
            }
            composable("${Screen.Strength.route}/{id}") {
                StrengthScreen(
                    navController = navController,
                    viewModel = challengeViewModel
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
                    navController = navController
                )
            }
            composable("${Screen.Walk.route}/{index}") {

            }

            // TEST REWARDS
            composable(Screen.TestReward.route) {
                AllRewardsScreen(
                    navController = navController,
                    challengeViewModel = challengeViewModel
                )
            }
        }
    }

    fun setViewModel(viewModel: ChallengeViewModel) {
        challengeViewModel = viewModel
    }
}