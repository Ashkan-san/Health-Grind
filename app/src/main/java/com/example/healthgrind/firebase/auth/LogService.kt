package com.example.healthgrind.firebase.auth

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}