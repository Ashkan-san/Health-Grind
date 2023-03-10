package com.example.healthgrind.firebase

import com.example.healthgrind.data.GenderType

data class User(
    val id: String = "",
    val isAnonymous: Boolean = true
)

// TODO in User einbauen?
data class PlayerModel(
    var name: String,
    var age: Int,
    var weight: Int,
    var height: Int,
    var gender: GenderType,
    var level: Int,
    var picture: String = "",
    var usageTime: Float = 0F
    // ...
)