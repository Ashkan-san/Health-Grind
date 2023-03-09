package com.example.healthgrind.firebase

interface LogService {
    fun logNonFatalCrash(throwable: Throwable)
}