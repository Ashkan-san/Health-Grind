package com.example.healthgrind.firebase.database.platform

import com.example.healthgrind.firebase.auth.debuglog.LogService
import com.example.healthgrind.firebase.auth.register.AccountService
import com.example.healthgrind.firebase.database.StorageService
import com.example.healthgrind.firebase.general.HealthGrindViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlatformViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService,
    private val storageService: StorageService
) : HealthGrindViewModel(logService) {

    var platforms = storageService.platforms

    /*fun getPlatformDataFromDb() {
        launchCatching {
            storageService.platforms.collect { value -> platformList = value }
        }
    }*/
}