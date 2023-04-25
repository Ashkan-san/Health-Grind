package com.example.healthgrind.firebase.auth.register

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.healthgrind.data.ExerciseType
import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.database.challenge.NewChallenge
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.reward.NewReward
import com.example.healthgrind.firebase.isValidEmail
import com.example.healthgrind.firebase.isValidPassword
import com.example.healthgrind.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {

    var userState = mutableStateOf(User())

    private val email
        get() = userState.value.email
    private val password
        get() = userState.value.password
    private val name
        get() = userState.value.name
    private val age
        get() = userState.value.age
    private val height
        get() = userState.value.height
    private val weight
        get() = userState.value.weight
    private val gender
        get() = userState.value.gender
    private val level
        get() = userState.value.level
    private val skill
        get() = userState.value.skill
    private val profile
        get() = userState.value.profile

    fun getUserDataFromDb() {
        launchCatching {
            userState.value = storageService.getCurrentUser()!!
        }
    }

    private fun createAllChallengesForUser(): List<NewChallenge> {
        return listOf(
            NewChallenge(
                title = "Schritte gehen",
                isMandatory = true,
                exerciseType = ExerciseType.WALK,
                goal = 2000,
            ),
            NewChallenge(
                title = "Schritte gehen",
                isMandatory = true,
                exerciseType = ExerciseType.WALK,
                goal = 2000,
            ),
            NewChallenge(
                title = "Schritte gehen",
                isMandatory = true,
                exerciseType = ExerciseType.WALK,
                goal = 4000,
            ),
            NewChallenge(
                title = "Schritte gehen",
                isMandatory = true,
                exerciseType = ExerciseType.WALK,
                goal = 4000,
            ),
            NewChallenge(
                title = "Minuten Laufen",
                isMandatory = true,
                exerciseType = ExerciseType.RUN,
                goal = 60000 * 15,
            ),
            NewChallenge(
                title = "Minuten Laufen",
                isMandatory = true,
                exerciseType = ExerciseType.RUN,
                goal = 60000 * 15,
            ),
            NewChallenge(
                title = "Minuten Laufen",
                isMandatory = true,
                exerciseType = ExerciseType.RUN,
                goal = 60000 * 30,
            ),
            NewChallenge(
                title = "Minuten Laufen",
                isMandatory = true,
                exerciseType = ExerciseType.RUN,
                goal = 60000 * 30,
            ),
            NewChallenge(
                title = "Push Ups",
                isMandatory = true,
                exerciseType = ExerciseType.STRENGTH,
                goal = 50,
            ),
            NewChallenge(
                title = "Squats",
                isMandatory = true,
                exerciseType = ExerciseType.STRENGTH,
                goal = 50,
            ),
        )
    }

    suspend fun setRewardAndGames(profile: String) {
        var platform = Platform()
        var reward1 = NewReward()
        var reward2 = NewReward()

        when (profile) {
            "ARDIAN" -> {
                // GAMES ERSTELLEN UND ZUORDNEN
                platform = Platform(
                    name = "FORTNITE/WARZONE",
                    image = "Games/warzone.jpg"
                )
                // REWARDS ERSTELLEN UND ZUORDNEN
                reward1 = NewReward(
                    title = "10€ Call of Duty Points",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                reward2 = NewReward(
                    title = "10€ V-Bucks",
                    value = "",
                    image = "Rewards/fortnite-code.jpeg"
                )
            }
            "ARIYAN" -> {
                platform = Platform(
                    name = "APPLE STORE",
                    image = "Games/apple.png"
                )
                reward1 = NewReward(
                    title = "10€ Apple Store Guthaben",
                    value = "",
                    image = "Rewards/apple-code.png"
                )
                reward2 = NewReward(
                    title = "10€ Apple Store Guthaben",
                    value = "",
                    image = "Rewards/apple-code.png"
                )
            }
            "BRIAN" -> {
                platform = Platform(
                    name = "NINTENDO ONLINE",
                    image = "Games/nintendo.jpg"
                )
                reward1 = NewReward(
                    title = "10€ Nintendo Guthaben",
                    value = "",
                    image = "Rewards/nintendo-code.png"
                )
                reward2 = NewReward(
                    title = "10€ Nintendo Guthaben",
                    value = "",
                    image = "Rewards/nintendo-code.png"
                )
            }
            "GERRIT" -> {
                platform = Platform(
                    name = "WARZONE",
                    image = "Games/warzone.jpg"
                )

                reward1 = NewReward(
                    title = "10€ Call of Duty Points",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                reward2 = NewReward(
                    title = "10€ Call of Duty Points",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
            }
            "JOSEPH" -> {
                platform = Platform(
                    name = "STEAM STORE",
                    image = "Games/steam.jpg"
                )
                reward1 = NewReward(
                    title = "10€ Steam Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                reward2 = NewReward(
                    title = "10€ Steam Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
            }
            "JUSTUS" -> {
                platform = Platform(
                    name = "APPLE STORE",
                    image = "Games/apple.png"
                )
                reward1 = NewReward(
                    title = "10€ Apple Store Guthaben",
                    value = "",
                    image = "Rewards/apple-code.png"
                )
                reward2 = NewReward(
                    title = "10€ Apple Store Guthaben",
                    value = "",
                    image = "Rewards/apple-code.png"
                )
            }
            "KATHLEEN" -> {
                platform = Platform(
                    name = "",
                    image = ""
                )
                reward1 = NewReward(
                    title = "",
                    value = "",
                    image = ""
                )
                reward2 = NewReward(
                    title = "",
                    value = "",
                    image = ""
                )
            }
            "KJELL" -> {
                platform = Platform(
                    name = "RIOT STORE",
                    image = "Games/riot.jpg"
                )
                reward1 = NewReward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                reward2 = NewReward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
            }
            "OLIVER" -> {
                platform = Platform(
                    name = "PLAYSTATION/PLAY STORE",
                    image = "Games/playstation.jpg"
                )
                reward1 = NewReward(
                    title = "10€ Playstation Guthaben",
                    value = "",
                    image = "Rewards/playstation-code.png"
                )
                reward2 = NewReward(
                    title = "10€ Play Store Guthaben",
                    value = "",
                    image = "Rewards/playstore-code.png"
                )
            }
            "QUAN" -> {
                platform = Platform(
                    name = "RIOT STORE",
                    image = "Games/riot.jpg"
                )
                reward1 = NewReward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                reward2 = NewReward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
            }
            else -> {
                println("KEIN RICHTIGER WERT")
            }
        }
        storageService.saveGame(platform)
        storageService.saveReward(reward1)
        storageService.saveReward(reward2)
    }

    fun onSignUpClick(navController: NavHostController): Boolean {
        if (!email.isValidEmail() || !password.isValidPassword()) {
            return false
        }

        launchCatching {
            // Anonymen Account erstellen und Accountdaten dazu linken
            accountService.createAnonymousAccount()
            accountService.linkAccount(email, password)
            //accountService.authenticate(email, password)

            // USER IN DB ERSTELLEN
            val user = User(id = accountService.currentUserId, email = email)
            storageService.saveCurrentUser(user)

            // ALLE CHALLENGES ERSTELLEN
            for (x in createAllChallengesForUser()) {
                storageService.saveChallenge(x)
            }

            // Screen wechseln, falls geklappt
            navController.navigate(Screen.NameInput.route) {
                launchSingleTop = true
                //popUpTo(Screen.SignUp.route) { inclusive = true }
            }
        }

        return true
    }

    fun onConfirmClick(
        navController: NavHostController,
        goTo: String,
        param: String,
        pref: SharedPreferences
    ): Boolean {
        launchCatching {
            when (param) {
                "name" -> storageService.updateCurrentUser(field = param, value = name)
                "age" -> storageService.updateCurrentUser(field = param, value = age)
                "height" -> storageService.updateCurrentUser(field = param, value = height)
                "weight" -> storageService.updateCurrentUser(field = param, value = weight)
                "gender" -> storageService.updateCurrentUser(field = param, value = gender)
                "level" -> storageService.updateCurrentUser(field = param, value = level)
                "profile" -> {
                    storageService.updateCurrentUser(field = param, value = profile)
                    setRewardAndGames(profile)
                }
                "skill" -> storageService.updateCurrentUser(field = param, value = skill)

                else -> {}
            }

            if (pref.getBoolean("firstStart", true)) {
                navController.navigate(goTo) {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            } else {
                navController.popBackStack()
            }

        }
        return true
    }

    // METHODEN UM USER DATEN ZU ÄNDERN
    fun onEmailChange(newValue: String) {
        userState.value = userState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        userState.value = userState.value.copy(password = newValue)
    }

    fun onNameChange(newValue: String) {
        userState.value = userState.value.copy(name = newValue)
    }

    fun onAgeChange(newValue: Int) {
        userState.value = userState.value.copy(age = newValue)
    }

    fun onHeightChange(newValue: Int) {
        userState.value = userState.value.copy(height = newValue)
    }

    fun onWeightChange(newValue: Int) {
        userState.value = userState.value.copy(weight = newValue)
    }

    fun onGenderChange(newValue: String) {
        userState.value = userState.value.copy(gender = newValue)
    }

    fun onSkillChange(newValue: String) {
        userState.value = userState.value.copy(skill = newValue)
    }

    fun onProfileChange(newValue: String) {
        userState.value = userState.value.copy(profile = newValue)
    }
}