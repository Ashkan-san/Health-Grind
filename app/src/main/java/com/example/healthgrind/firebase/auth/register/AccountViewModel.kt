package com.example.healthgrind.firebase.auth.register

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.database.platform.Platform
import com.example.healthgrind.firebase.database.reward.Reward
import com.example.healthgrind.firebase.general.HealthGrindViewModel
import com.example.healthgrind.support.Screen
import com.example.healthgrind.support.createAllChallengesForUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
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

    suspend fun setProfile(profile: String) {
        var platform = Platform()
        var reward1 = Reward()
        var reward2 = Reward()
        var imagePath = "Users/"

        when (profile) {
            "ARDIAN" -> {
                // GAMES ERSTELLEN UND ZUORDNEN
                platform = Platform(
                    name = "PLAYSTATION STORE",
                    image = "Platforms/playstation.jpg"
                )
                // REWARDS ERSTELLEN UND ZUORDNEN
                reward1 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                reward2 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                imagePath += "ardian.jpg"
            }
            "BRIAN" -> {
                platform = Platform(
                    name = "STEAM STORE",
                    image = "Platforms/steam.jpg"
                )
                reward1 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                reward2 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                imagePath += "brian.jpg"
            }
            "GERRIT" -> {
                platform = Platform(
                    name = "PLAYSTATION STORE",
                    image = "Platforms/playstation.jpg"
                )
                reward1 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                reward2 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                imagePath += "gerrit.jpg"
            }
            "JILL" -> {
                platform = Platform(
                    name = "STEAM STORE",
                    image = "Platforms/steam.jpg"
                )
                reward1 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                reward2 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                imagePath += "jill.jpg"
            }
            "JOSEPH" -> {
                platform = Platform(
                    name = "PLAYSTATION STORE",
                    image = "Platforms/playstation.jpg"
                )
                reward1 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                reward2 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/cod-code.jpg"
                )
                imagePath += "joseph.jpg"
            }
            "JUSTUS" -> {
                platform = Platform(
                    name = "STEAM STORE",
                    image = "Platforms/steam.jpg"
                )
                reward1 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                reward2 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                imagePath += "justus.jpg"
            }
            "KJELL" -> {
                platform = Platform(
                    name = "RIOT STORE",
                    image = "Platforms/riot.jpg"
                )
                reward1 = Reward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                reward2 = Reward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                imagePath += "kjell.jpg"
            }

            "LEO" -> {
                platform = Platform(
                    name = "STEAM STORE",
                    image = "Platforms/steam.jpg"
                )
                reward1 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                reward2 = Reward(
                    title = "10€ Steam Store Guthaben",
                    value = "",
                    image = "Rewards/steam-code.png"
                )
                imagePath += "leo.jpg"
            }

            "OLIVER" -> {
                platform = Platform(
                    name = "PLAYSTATION STORE",
                    image = "Platforms/playstation.jpg"
                )
                reward1 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/playstation-code.png"
                )
                reward2 = Reward(
                    title = "10€ PS Store Guthaben",
                    value = "",
                    image = "Rewards/playstation-code.png"
                )
                imagePath += "oliver.jpg"
            }
            "QUAN" -> {
                platform = Platform(
                    name = "RIOT STORE",
                    image = "Platforms/riot.jpg"
                )
                reward1 = Reward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                reward2 = Reward(
                    title = "10€ Riot Points",
                    value = "",
                    image = "Rewards/riot-code.jpg"
                )
                imagePath += "quan.jpg"
            }
            else -> {
                Log.d(TAG, "USER NICHT GEFUNDEN.")
            }
        }
        storageService.savePlatform(platform)
        storageService.saveReward(reward1)
        storageService.saveReward(reward2)
        storageService.updateCurrentUser("image", imagePath)
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
            navController.navigate(Screen.ProfileInput.route) {
                launchSingleTop = true
            }
        }

        return true
    }

    fun onConfirmClick(
        navController: NavHostController,
        goTo: String,
        param: String,
        pref: SharedPreferences
    ) {
        launchCatching {
            when (param) {
                "profile" -> {
                    storageService.updateCurrentUser(field = param, value = profile)
                    setProfile(profile)
                }

                "name" -> storageService.updateCurrentUser(field = param, value = name)
                "age" -> storageService.updateCurrentUser(field = param, value = age)
                "height" -> storageService.updateCurrentUser(field = param, value = height)
                "weight" -> storageService.updateCurrentUser(field = param, value = weight)
                "gender" -> storageService.updateCurrentUser(field = param, value = gender)
                "skill" -> storageService.updateCurrentUser(field = param, value = skill)
                "level" -> storageService.updateCurrentUser(field = param, value = level)
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
    }

    fun getUserDataFromDb() {
        launchCatching {
            userState.value = storageService.getCurrentUser()!!
        }
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