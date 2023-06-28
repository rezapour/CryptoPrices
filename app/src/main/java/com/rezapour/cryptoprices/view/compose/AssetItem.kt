package com.rezapour.cryptoprices.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.model.Asset

@Composable
fun AssetItem(
    asset: Asset,
    checked: Boolean,
    onCheckedChanged: () -> Unit,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClicked(asset.assetId) },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ImageLoader(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .padding(4.dp), imageUrl = Constance.getImageUrl(asset.idIcon?.replace("-", ""))
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(text = asset.name, style = MaterialTheme.typography.labelLarge)
                Text(
                    text = asset.assetId,
                    modifier = modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }

            IconButton(onClick = onCheckedChanged, modifier = Modifier.padding(0.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = if (checked) Color.Red else Color.LightGray
                )
            }
        }
    }
}
