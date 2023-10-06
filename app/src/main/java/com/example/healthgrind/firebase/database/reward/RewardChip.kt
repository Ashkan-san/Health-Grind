package com.example.healthgrind.firebase.database.reward

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.ChipDefaults
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.example.healthgrind.support.LoadImageFromStorage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RewardChip(
    reward: Reward,
    onRedeem: () -> Unit
) {
    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        label = {
            Text(
                text = reward.title,
                modifier = Modifier
                    .fillMaxWidth(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        secondaryLabel = {
            Text(
                text = "Code: " + if (reward.redeemed) reward.value else "*".repeat(reward.value.length),
                maxLines = 1,
                //overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .basicMarquee()
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
        colors = ChipDefaults.chipColors(
            backgroundColor = if (reward.unlocked) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.primary
        ),
        onClick = {
            onRedeem()
        },
        enabled = !reward.redeemed
    )
}
