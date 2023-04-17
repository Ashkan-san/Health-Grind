package com.example.healthgrind.presentation.screens

import android.content.SharedPreferences
import android.view.MotionEvent
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.example.healthgrind.R
import com.example.healthgrind.firebase.auth.register.SignUpViewModel
import com.example.healthgrind.presentation.navigation.Screen
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

@Composable
fun AgeInputScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel = hiltViewModel(),
    pref: SharedPreferences
) {
    DatePicker(
        onDateConfirm = {
            viewModel.onAgeChange(it)
            viewModel.onConfirmClick(navController, Screen.HeightInput.route, "age", pref)
        }
    )
}

private fun calculateAge(date: LocalDate): Int {
    return Period.between(
        date,
        LocalDate.now()
    ).years
}

@Composable
fun DatePicker(
    onDateConfirm: (Int) -> Unit,
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now()
) {
    // STATES VON TAG, MONAT, JAHR
    val yearState = rememberPickerState(
        initialNumberOfOptions = date.year,
        initiallySelectedOption = date.year - 1
    )
    val monthState = rememberPickerState(
        initialNumberOfOptions = 12,
        initiallySelectedOption = date.monthValue - 1
    )
    val maxDayInMonth by remember {
        derivedStateOf {
            val firstDayOfMonth =
                LocalDate.of(
                    yearState.selectedOption + 1,
                    monthState.selectedOption + 1,
                    1
                )
            firstDayOfMonth.with(TemporalAdjusters.lastDayOfMonth()).dayOfMonth
        }
    }
    val dayState = rememberPickerState(
        initialNumberOfOptions = maxDayInMonth,
        initiallySelectedOption = date.dayOfMonth - 1
    )

    val focusRequester1 = remember { FocusRequester() }
    val focusRequester2 = remember { FocusRequester() }
    val focusRequester3 = remember { FocusRequester() }

    LaunchedEffect(maxDayInMonth) {
        if (maxDayInMonth != dayState.numberOfOptions) {
            dayState.numberOfOptions = maxDayInMonth
        }
    }
    val monthNames = remember {
        val monthFormatter = DateTimeFormatter.ofPattern("MMM")
        val months = 1..12
        months.map {
            // Translate month index into 3-character month string e.g. Jan.
            LocalDate.of(2022, it, 1).format(monthFormatter)
        }
    }

    var selectedColumn by remember { mutableStateOf(0) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val boxConstraints = this

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                text = "Alter"
            )

            Spacer(Modifier.height(16.dp))

            // Überschriften
            Text(
                text = when (selectedColumn) {
                    0 -> "Tag"
                    1 -> "Monat"
                    else -> "Jahr"
                },
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.button,
                maxLines = 1
            )

            // Spacing Kram
            val weightsToCenterVertically = 0.5f
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(weightsToCenterVertically)
            )
            val spacerWidth = 8.dp
            val dayWidth = 54.dp
            val monthWidth = 80.dp
            val yearWidth = 128.dp
            val offset = when (selectedColumn) {
                0 -> (boxConstraints.maxWidth - dayWidth) / 2
                1 -> (boxConstraints.maxWidth - monthWidth) / 2 - dayWidth - spacerWidth
                else -> (boxConstraints.maxWidth - yearWidth) / 2 - monthWidth
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(offset)
            ) {

                if (selectedColumn < 2) {
                    DatePickerImpl(
                        state = dayState,
                        readOnly = selectedColumn != 0,
                        onSelected = { selectedColumn = 0 },
                        text = { day: Int -> "%d".format(day + 1) },
                        width = dayWidth,
                        focusRequester = focusRequester1,
                        contentDescription = "%d".format(dayState.selectedOption + 1)
                    )
                    Spacer(modifier = Modifier.width(spacerWidth))
                }

                DatePickerImpl(
                    state = monthState,
                    readOnly = selectedColumn != 1,
                    onSelected = { selectedColumn = 1 },
                    text = { month: Int -> monthNames[month] },
                    width = monthWidth,
                    focusRequester = focusRequester2,
                    contentDescription = monthNames[monthState.selectedOption]
                )

                if (selectedColumn > 0) {
                    Spacer(modifier = Modifier.width(spacerWidth))
                    DatePickerImpl(
                        state = yearState,
                        readOnly = selectedColumn != 2,
                        onSelected = { selectedColumn = 2 },
                        text = { year: Int -> "%4d".format(year + 1) },
                        width = yearWidth,
                        focusRequester = focusRequester3,
                        contentDescription = "%4d".format(yearState.selectedOption + 1)
                    )
                }
            }

            // Spacing Kram
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .weight(weightsToCenterVertically)
            )

            // Confirm Button
            Button(
                onClick = {
                    val confirmedDate = LocalDate.of(
                        yearState.selectedOption + 1,
                        monthState.selectedOption + 1,
                        dayState.selectedOption + 1
                    )
                    onDateConfirm(calculateAge(confirmedDate))
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

            // Spacing Kram
            Spacer(Modifier.height(12.dp))
        }

        LaunchedEffect(selectedColumn) {
            listOf(focusRequester1, focusRequester2, focusRequester3)[selectedColumn]
                .requestFocus()
        }
    }
}

@Composable
private fun DatePickerImpl(
    state: PickerState,
    readOnly: Boolean,
    onSelected: () -> Unit,
    text: (option: Int) -> String,
    focusRequester: FocusRequester,
    contentDescription: String?,
    width: Dp
) {
    CustomPicker(
        readOnly = readOnly,
        state = state,
        focusRequester = focusRequester,
        modifier = Modifier.size(width, 100.dp),
        contentDescription = contentDescription,
        onSelected = onSelected
    ) { option ->
        NumberPiece(
            selected = !readOnly,
            onSelected = onSelected,
            text = text(option),
            style = MaterialTheme.typography.display2
        )
    }

}

@Composable
internal fun CustomPicker(
    state: PickerState,
    readOnly: Boolean,
    modifier: Modifier,
    focusRequester: FocusRequester,
    contentDescription: String?,
    readOnlyLabel: @Composable (BoxScope.() -> Unit)? = null,
    flingBehavior: FlingBehavior = PickerDefaults.flingBehavior(state = state),
    onSelected: () -> Unit = {},
    option: @Composable PickerScope.(optionIndex: Int) -> Unit
) {
    Picker(
        state = state,
        contentDescription = contentDescription,
        onSelected = onSelected,
        modifier = modifier
            .focusRequester(focusRequester)
            .focusable(),
        flingBehavior = flingBehavior,
        readOnly = readOnly,
        readOnlyLabel = readOnlyLabel,
        option = option
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun NumberPiece(
    selected: Boolean,
    onSelected: () -> Unit,
    text: String,
    style: TextStyle
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val modifier = Modifier
            .align(Alignment.Center)
            .wrapContentSize()
        Text(
            text = text,
            maxLines = 1,
            style = style,
            color = if (selected) MaterialTheme.colors.secondary
            else MaterialTheme.colors.onBackground,
            modifier = if (selected) {
                modifier
            } else {
                modifier.pointerInteropFilter {
                    if (it.action == MotionEvent.ACTION_DOWN) onSelected()
                    true
                }
            }
        )
    }
}