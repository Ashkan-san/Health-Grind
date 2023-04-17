package com.example.healthgrind.firebase.database.reward

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

@Composable
@ExperimentalMaterialApi
fun RewardChip(
    reward: Reward,
    onCheckChange: () -> Unit
) {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        label = {
            Text(
                text = reward.title,
                modifier = Modifier.fillMaxWidth()
            )
        },
        secondaryLabel = {
            Text(
                text = "Code: " + if (reward.redeemed) reward.value else "*********",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            LoadImageFromStorage(reward)
        },
        onClick = {
            onCheckChange()
        },
        enabled = !reward.redeemed
    )
}

@Composable
fun LoadImageFromStorage(item: Reward) {
    var link by remember { mutableStateOf("") }
    val storageRef = Firebase.storage.reference

    storageRef.child(item.image).downloadUrl.addOnSuccessListener {
        link = it.toString()
    }.addOnFailureListener {
        println("DIGGA, BILD NICHT GEFUNDEN.")
    }
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(link)
            //.placeholder(R.drawable.reward)
            .build(),
        contentDescription = "Image",
        modifier = Modifier
            .size(24.dp)
            .wrapContentSize(align = Alignment.Center)
    )
}