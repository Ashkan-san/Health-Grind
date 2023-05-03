package com.example.healthgrind.firebase.database.reward

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Text
import com.example.healthgrind.data.LoadImageFromStorage

@Composable
@ExperimentalMaterialApi
fun RewardChip(
    reward: NewReward,
    onRedeem: () -> Unit
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
            LoadImageFromStorage(
                reward.image,
                Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        },
        onClick = {
            onRedeem()
        },
        enabled = !reward.redeemed
    )
}
