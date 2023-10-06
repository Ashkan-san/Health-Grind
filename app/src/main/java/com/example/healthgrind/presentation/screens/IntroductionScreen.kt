package com.example.healthgrind.presentation.screens

import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.R
import com.example.healthgrind.support.Screen

@Composable
fun IntroductionScreen(navController: NavHostController, vibrator: Vibrator) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.spacedBy(0.dp),
        state = listState
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(100.dp)
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                text = "Willkommen bei HealthGrind!"
            )
        }
        item {
            val annotatedText = buildAnnotatedString {
                append("\nDie erste App, welche deine sportliche Aktivität mit In-Game-Rewards belohnt!\n\n")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)) {
                    append("Wie funktioniert es?\n\n")
                }
                append(
                    "Du wählst dein Game, die Art von Exercise und dann eine Challenge aus, die du bewältigen willst.\n" +
                            "Deine Bewegungen werden dann entweder getracked oder du musst deine Fortschritte selber eingeben.\n" +
                            "Hast du genügend Challenges abgeschlossen, kriegst du deinen Reward!\n\n"
                )

                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)) {
                    append("Arten von Challenges\n\n")
                }
                append(
                    "- Schritte gehen: nimm deine Uhr und gehe die Anzahl Schritte zu fuß.\n" +
                            "- Zeit laufen: sobald die Zeit losgeht, jogge oder laufe los. Solltest du Pause machen wollen, drücke den Pauseknopf.\n" +
                            "- Kraftübung: führe die Anzahl Wiederholungen einer Kraftübung aus.\n\n"
                )

                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline, fontWeight = FontWeight.Bold)) {
                    append("Bemerkungen\n\n")
                }
                append(
                    "- Verlässt du einen Challenge-Screen wird die Challenge pausiert!\n" +
                            "- Du musst eine Challenge nicht komplett am Stück durchziehen, pausiere sie und mach wann anders weiter.\n" +
                            "- Das €-Zeichen ist für eine Challenge, die dich einen Schritt weiter an deinen Reward bringt. Das Herz-Zeichen wiederum ist eine freiwillige Challenge, würde mich trotzdem sehr freuen wenn du möglichst viele von ihnen erledigst! :)\n\n"
                )

                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("App made by:\n")
                }
                append("Ashkan Haghighi Fashi\n")
            }
            Text(
                text = annotatedText,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
            )
        }
        item {
            Button(onClick = {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                navController.navigate(Screen.SignUp.route)
            }
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = "Input"
                )
            }
        }
    }
}