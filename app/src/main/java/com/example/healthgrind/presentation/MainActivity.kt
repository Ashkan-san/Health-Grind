package com.example.healthgrind.presentation

import android.app.Application
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
import com.example.healthgrind.data.ChallengeModel
import com.example.healthgrind.data.DataSource
import com.example.healthgrind.presentation.navigation.Screen
import com.example.healthgrind.presentation.screens.*
import com.example.healthgrind.presentation.theme.HealthGrindTheme
import com.example.healthgrind.viewmodel.MainViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import java.lang.reflect.Type
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dataSource: DataSource
    private lateinit var pref: SharedPreferences
    private lateinit var setPref: SharedPreferences
    private lateinit var dataPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // SCREEN ANLASSEN ZUM DEBUGGEN
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        setContent {
            HealthGrindTheme {
                navController = rememberSwipeDismissableNavController()
                dataSource = DataSource()
                mainViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

                pref = getSharedPreferences("userData", MODE_PRIVATE)
                setPref = getSharedPreferences("myPref", MODE_PRIVATE)
                dataPref = getSharedPreferences("dataPref", MODE_PRIVATE)

                // TEST/STANDARTDATEN
                pref.edit().putString("name", "Ash").putInt("level", 100).putInt("height", 180)
                    .putInt("weight", 85).putString("gender", "MALE").putInt("age", 23)
                    .putString("Skill-Level", "PRO").apply()

                // FIRST START
                if (setPref.getBoolean("firststart", true)) {
                    InitNavHost(true)
                } else {
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
            navController = navController, startDestination = startDestination
        ) {
            composable(Screen.Start.route) {
                StartScreen(navController = navController)
            }
            composable(Screen.FirstStart.route) {
                FirstStartScreen(navController = navController)
            }
            composable(Screen.Debug.route) {
                DebugScreen(navController = navController)
            }
            composable(Screen.Games.route) {
                GamesScreen(navController = navController, dataSource = dataSource)
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
                    dataSource = dataSource,
                    id = it.arguments?.getString("id"),
                    id2 = it.arguments?.getString("id2"),
                    pref = pref,
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
            // PLAYER INFO UND INPUT
            composable(Screen.PlayerInfo.route) {
                PlayerInfoScreen(navController = navController, pref = pref)
            }
            composable(Screen.NameInput.route) {
                NameInputScreen(navController = navController, pref = pref, setPref = setPref)
            }
            composable(Screen.AgeInput.route) {
                AgeInputScreen(navController = navController, pref = pref, setPref = setPref)
            }
            composable(Screen.GenderInput.route) {
                GenderInputScreen(navController = navController, pref = pref, setPref = setPref)
            }
            composable(Screen.SkillInput.route) {
                SkillInputScreen(navController = navController, pref = pref, setPref = setPref)
            }
            composable(Screen.WeightInput.route) {
                WeightInputScreen(navController = navController, pref = pref, setPref = setPref)
            }
            composable(Screen.HeightInput.route) {
                HeightInputScreen(navController = navController, pref = pref, setPref = setPref)
            }

            // SIGN UP
            composable(Screen.SignUp.route) {
                SignUpScreen(navController = navController)
            }
        }
    }

    fun saveListToPref(list: List<ChallengeModel?>?, key: String?) {
        val prefs: SharedPreferences = getSharedPreferences("dataPref", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        val gson = Gson()
        val json: String = gson.toJson(list)
        editor.putString(key, json)
        editor.apply()
    }

    fun getListFromPref(key: String?): List<ChallengeModel?>? {
        val prefs: SharedPreferences = getSharedPreferences("dataPref", MODE_PRIVATE)
        val gson = Gson()
        val json: String? = prefs.getString(key, null)
        val type: Type = object : TypeToken<List<ChallengeModel?>?>() {}.type
        return gson.fromJson(json, type)
    }

    fun dataClassToString(instance: Any) {
        val sb = StringBuilder()
        sb.append("data class ${instance::class.qualifiedName} (")
        var prefix = ""
        instance::class.memberProperties.forEach {
            sb.append(prefix)
            prefix = ","
            sb.append("${it.name} = ${it.getter.call(instance)}")
        }
        sb.append(")")
        println(sb.toString())
    }
}

