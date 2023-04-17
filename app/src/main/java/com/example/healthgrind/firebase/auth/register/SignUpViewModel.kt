package com.example.healthgrind.firebase.auth.register

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
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

    fun getUserDataFromDb() {
        launchCatching {
            //user = storageService.getCurrentUser()!!
            userState.value = storageService.getCurrentUser()!!
            //userState.value.name = user.name
        }
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
            accountService.createUser(email = email)

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
                "name" -> accountService.updateUser(field = param, value = name)
                "age" -> accountService.updateUser(field = param, value = age)
                "height" -> accountService.updateUser(field = param, value = height)
                "weight" -> accountService.updateUser(field = param, value = weight)
                "gender" -> accountService.updateUser(field = param, value = gender)
                "level" -> accountService.updateUser(field = param, value = level)
                "skill" -> {
                    accountService.updateUser(field = param, value = skill)
                }
                else -> {}
            }

            //user = storageService.getUser(accountService.currentUserId)

            if (pref.getBoolean("firstStart", true)) {
                println("FIRST START TRUE")
                navController.navigate(goTo) {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            } else {
                println("FIRST START FALSE, DESHALB ZURÜCK")
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
}