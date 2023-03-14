package com.example.healthgrind.firebase.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.AccountService
import com.example.healthgrind.firebase.auth.LogService
import com.example.healthgrind.firebase.isValidEmail
import com.example.healthgrind.firebase.isValidPassword
import com.example.healthgrind.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : HealthGrindViewModel(logService) {

    // IST EIN SET AUS MAIL UND PW
    var uiState = mutableStateOf(SignUpUiState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    // METHODEN UM MAIL, PW ZU ÄNDERN
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    /*fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }*/

    fun onSignUpClick(navController: NavHostController): Boolean {
        if (!email.isValidEmail() || !password.isValidPassword()) {
            return false
        }

        /*if (!password.passwordMatches(uiState.value.repeatPassword)) {
            //SnackbarManager.showMessage(AppText.password_match_error)
            println("Zweites Passwort ist falsch")
            return
        }*/

        launchCatching {
            // Anonymen Account erstellen und Accountdaten dazu linken
            accountService.createAnonymousAccount()
            accountService.linkAccount(email, password)

            // Screen wechseln, falls geklappt
            navController.navigate(Screen.Start.route) {
                launchSingleTop = true
                popUpTo(Screen.SignUp.route) { inclusive = true }
            }
        }

        return true
    }
}