package com.example.healthgrind.firebase.database.reward

import com.google.firebase.firestore.DocumentId

data class Reward(
    @DocumentId val id: String = "",
    val title: String = "",
    val value: String = "",
    val unlocked_by: String = "",
    val redeemed: Boolean = false,
    val image: String = ""
)