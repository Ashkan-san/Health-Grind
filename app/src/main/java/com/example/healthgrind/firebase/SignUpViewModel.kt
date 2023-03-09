package com.example.healthgrind.firebase

import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    // openAndPopUp: (String, String) -> Unit
    fun onSignUpClick(navController: NavHostController) {
        if (!email.isValidEmail()) {
            //SnackbarManager.showMessage(AppText.email_error)
            println("Mail ist falsch")
            return
        }

        if (!password.isValidPassword()) {
            //SnackbarManager.showMessage(AppText.password_error)
            println("Passwort ist falsch")
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            //SnackbarManager.showMessage(AppText.password_match_error)
            println("Zweites Passwort ist falsch")
            return
        }

        launchCatching {
            // Versuchen anzumelden
            accountService.linkAccount(email, password)

            // Screen wechseln, falls geklappt
            navController.navigate(Screen.Start.route) {
                launchSingleTop = true
                popUpTo(Screen.SignUp.route) { inclusive = true }
            }
        }
    }
}