package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.itemsIndexed
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.*
import com.example.healthgrind.R
import com.example.healthgrind.data.SkillType
import com.example.healthgrind.firebase.auth.register.SignUpViewModel
import com.example.healthgrind.presentation.navigation.Screen

@Composable
fun SkillInputScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel(),
    pref: SharedPreferences
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)
    val radioOptions = SkillType.getList()
    val radioBools = listOf(
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) }
    )

    var skill = viewModel.userState.value.skill

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {
        item {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Sport-Level"
            )
        }

        itemsIndexed(radioOptions) { index, option ->
            ToggleChip(
                modifier = Modifier.size(180.dp, 50.dp),
                label = {
                    Text(
                        text = option,
                        textAlign = TextAlign.Center
                    )
                },

                checked = radioBools[index].value,
                onCheckedChange = {
                    radioBools.forEach { it.value = false }
                    radioBools[index].value = true

                    skill = option
                },
                toggleControl = {
                    RadioButton(
                        selected = radioBools[index].value,
                        modifier = Modifier.semantics {
                            this.contentDescription = if (radioBools[index].value) "On" else "Off"
                        }
                    )
                },
                appIcon = {
                    var id = 0
                    when (option) {
                        "BEGINNER" -> id = R.drawable.empty
                        "ADVANCED" -> id = R.drawable.half
                        "PRO" -> id = R.drawable.full
                    }
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = "Icon",
//                        modifier = Modifier
//                            .size(24.dp)
//                            .wrapContentSize(align = Alignment.Center)
                    )
                }
            )
        }
        item {
            // Confirm Button
            Button(
                onClick = {
                    viewModel.onSkillChange(skill)
                    viewModel.onConfirmClick(navController, Screen.Start.route, "skill", pref)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Confirm",
                    modifier = Modifier
                        .size(24.dp)
                        .wrapContentSize(align = Alignment.Center)
                )

            }
        }
    }
}