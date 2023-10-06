package com.example.healthgrind.firebase.auth.register

data class User(
    // AUTH DATA
    val id: String = "",
    var email: String = "",
    val password: String = "",
    var image: String = "Users/ash.jpg",

    // CUSTOM DATA
    var name: String = "",
    var age: Int = 0,
    var weight: Int = 0,
    var height: Int = 0,
    var gender: String = "",
    var skill: String = "",
    var level: Int = 0,
    var profile: String = "",

    // TODO noch messen bzw irgendwo her bekommen
    var usageTime: Float = 0F,
) {
    operator fun iterator(): List<Pair<String, Any>> {
        return listOf(
            "Name" to name, "Alter" to age, "Gewicht" to weight, "Größe" to height,
            "Geschlecht" to gender, "Sport-Level" to skill, "HealthGrind-Level" to level
        )
    }
}