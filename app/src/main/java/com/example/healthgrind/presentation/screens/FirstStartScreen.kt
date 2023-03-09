package com.example.healthgrind.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.example.healthgrind.R
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun FirstStartScreen(navController: NavHostController) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = "Welcome to HealthGrind!"
            )
        }
        item {
            Text(
                text = "\nThe first App that rewards your break from games with ingame-rewards!\n\n" +
                        "How does it work?\n\n" +
                        "You choose your preferred game, an exercise type and the challenge you want to complete.\n" +
                        "Your movement will be tracked then.\n" +
                        "After fullfilling your goal you get your reward,\n" +
                        "have fun!\n\n" +
                        "Made by:\n" +
                        "Ashkan Haghighi Fashi\n Joseph Abasszada\n Oliver Tano Schlichting\n",
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
            )
        }
        item {
            Button(onClick = {
                navController.navigate(Screen.NameInput.route)
                //navController.navigate(Screen.AgeInput.route)
            }
            ) {
                Icon(
                    painter = painterResource(R.drawable.check),
                    contentDescription = "Input"
                )
            }
        }
    }
}