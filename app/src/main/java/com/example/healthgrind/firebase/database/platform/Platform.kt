package com.example.healthgrind.firebase.database.platform

import com.google.firebase.firestore.DocumentId

data class Platform(
    @DocumentId val id: String = "",
    val name: String = "",
    val image: String = "",
)