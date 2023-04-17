package com.example.healthgrind.firebase.database.challenge

import com.example.healthgrind.firebase.HealthGrindViewModel
import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.database.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    logService: LogService,
    private val storageService: StorageService,
) : HealthGrindViewModel(logService) {

    val challenges = storageService.challenges

    /*fun onChallengeFinishChange(challenge: Challenge) {
        launchCatching { storageService.update(challenge.copy(finished = challenge.finished)) }
    }*/

    /*fun onAddClick(openScreen: (String) -> Unit) = openScreen(EDIT_TASK_SCREEN)

    fun onSettingsClick(openScreen: (String) -> Unit) = openScreen(SETTINGS_SCREEN)

    fun onTaskActionClick(openScreen: (String) -> Unit, task: Task, action: String) {
        when (TaskActionOption.getByTitle(action)) {
            TaskActionOption.EditTask -> openScreen("$EDIT_TASK_SCREEN?$TASK_ID={${task.id}}")
            TaskActionOption.ToggleFlag -> onFlagTaskClick(task)
            TaskActionOption.DeleteTask -> onDeleteTaskClick(task)
        }
    }

    private fun onFlagTaskClick(task: Task) {
        launchCatching { storageService.update(task.copy(flag = !task.flag)) }
    }

    private fun onDeleteTaskClick(task: Task) {
        launchCatching { storageService.delete(task.id) }
    }*/
}