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
import com.example.healthgrind.data.GenderType
import com.example.healthgrind.firebase.auth.register.SignUpViewModel

@Composable
fun GenderInputScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel(),
    pref: SharedPreferences
) {
    val listState = rememberScalingLazyListState(initialCenterItemIndex = 0)
    val radioOptions = GenderType.getList()
    val radioBools = listOf(
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) },
        remember { mutableStateOf(false) }
    )

    var gender = viewModel.userState.value.gender

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
                text = "Geschlecht"
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

                    gender = option
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
                        "MALE" -> id = R.drawable.male
                        "FEMALE" -> id = R.drawable.female
                        "OTHER" -> id = R.drawable.trans
                    }
                    Icon(
                        painter = painterResource(id = id),
                        contentDescription = "Icon"
                    )
                }
            )
        }
        item {
            // Confirm Button
            Button(
                onClick = {
                    viewModel.onGenderChange(gender)
                    viewModel.onConfirmClick(
                        navController,
                        Screen.ProfileInput.route,
                        "gender",
                        pref
                    )
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
