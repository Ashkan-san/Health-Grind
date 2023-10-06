package com.example.healthgrind.firebase.database.challenge

data class Statistics(
    //@DocumentId val id: String = "",
    var mandatoryCount: Int = 0,
    var optionalCount: Int = 0,
    var mandatoryGoal: Int = 5,
    var completedSteps: Int = 0

    // TODO evt Nutzungszeit,
)