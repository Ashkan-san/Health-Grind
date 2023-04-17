package com.example.healthgrind.firebase.auth.debuglog

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}