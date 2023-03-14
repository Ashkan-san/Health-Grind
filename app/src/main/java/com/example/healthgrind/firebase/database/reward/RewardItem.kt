package com.example.healthgrind.firebase.database.reward

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Chip
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.healthgrind.R

@Composable
@ExperimentalMaterialApi
fun RewardItem(
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
                modifier = Modifier.fillMaxWidth(),
                //color = MaterialTheme.colors.onPrimary
            )
        },
        secondaryLabel = {
            Text(
                text = "Code: " + reward.value,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                // todo anderes bild
                painter = painterResource(id = R.drawable.swords),
                contentDescription = "Icon Name",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        },
        onClick = {
            onCheckChange()
        },
        enabled = !reward.redeemed
    )
}