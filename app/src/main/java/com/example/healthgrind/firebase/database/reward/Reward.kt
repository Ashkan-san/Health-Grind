package com.example.healthgrind.firebase.database.reward

import com.google.firebase.firestore.DocumentId

data class Reward(
    @DocumentId val id: String = "",
    val title: String = "",
    val value: String = "",
    val unlocked: Boolean = false,
    val redeemed: Boolean = false,
    val image: String = ""
)