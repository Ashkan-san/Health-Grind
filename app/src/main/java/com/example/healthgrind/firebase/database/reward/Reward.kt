package com.example.healthgrind.firebase.database.reward

import com.google.firebase.firestore.DocumentId

data class NewReward(
    @DocumentId val id: String = "",
    val title: String = "",
    val value: String = "",
    val redeemed: Boolean = false,
    val image: String = ""
)