package com.example.healthgrind.presentation.screens

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.presentation.activities.MapActivity
import com.example.healthgrind.presentation.activities.MapsLocationActivity
import com.example.healthgrind.support.Screen

@Composable
fun DebugScreen(navController: NavHostController) {
    val context = LocalContext.current

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = rememberScalingLazyListState()
    ) {

        item {
            Button(onClick = {
                navController.navigate(Screen.Permission.route)
            }) {
                Text(text = "Permission")
            }
        }
        item {
            Button(onClick = {
                navController.navigate(Screen.AllRewards.route)
            }) {
                Text(text = "Test Rewards")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.SignUp.route)
            }) {
                Text(text = "Sign Up")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.Strength.route)
            }) {
                Text(text = "Strength")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.Run.route)
            }) {
                Text(text = "Running")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.GenderInput.route)
            }) {
                Text(text = "Gender")
            }
        }


        item {
            Button(onClick = {
                context.startActivity(Intent(context, MapsLocationActivity::class.java))
            }) {
                Text(text = "Map Location")
            }
        }

        item {
            Button(onClick = {
                context.startActivity(Intent(context, MapActivity::class.java))
            }) {
                Text(text = "Map")
            }
        }

        item {
            Button(onClick = {
                //context.startActivity(Intent(context, HealthActivity::class.java))
            }) {
                Text(text = "Health")
            }
        }

        item {
            Button(onClick = {
                navController.navigate(Screen.NameInput.route)
            }) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Input"
                )
            }
        }
    }
}

