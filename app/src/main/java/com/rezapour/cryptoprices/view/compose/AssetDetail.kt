package com.rezapour.cryptoprices.view.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.ui.theme.CryptoPricesTheme
import com.rezapour.cryptoprices.view.view_models.AssetDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetDetailScreen(viewModel: AssetDetailViewModel = hiltViewModel()) {
    Log.d("REZAPOUR", "Start")
    viewModel.loadAssetDetail("BTC")

    Scaffold(topBar = { TopBar() }) { paddingValues ->
        Screen(viewModel, Modifier.padding(paddingValues))
    }


}

@Composable
fun Screen(viewModel: AssetDetailViewModel, modifier: Modifier = Modifier) {
    when (val state = viewModel.dataState.collectAsState().value) {
        is DataState.DefaultError -> {
            Log.d("REZAPOUR", "error")
        }

        is DataState.Error -> {
            Log.d("REZAPOUR", "error")
        }

        DataState.Loading -> {
            Loading()
        }

        is DataState.Success -> ContentSection(asset = state.data,modifier=modifier)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(title = { Text(text = stringResource(R.string.detail)) }, navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
        }
    })
}

@Composable
fun ContentSection(asset: AssetDetail, modifier: Modifier = Modifier) {
    Log.d("REZAPOUR", "ss")
    Column(modifier = modifier) {
        DetailFirstPart(imageUrl = "", assetId = asset.assetId, assetName = asset.name)
        DetailsSecondPart(
            symbol = asset.dataSymbolsCount.toString(),
            horus = asset.volume1hrsUsd.toString(),
            day = asset.volume1dayUsd.toString(),
            month = asset.volume1mthUsd.toString(),
            price = asset.priceUsd.toString()
        )

        DetailRatePart(
            time = asset.exchnageTime,
            rate = asset.rate.toString(),
            quote = asset.assetIdQuote
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailFirstPart(
    imageUrl: String,
    assetId: String,
    assetName: String,
    modifier: Modifier = Modifier
) {

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            model = imageUrl,
            contentDescription = "Hello",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(4.dp)
        ) {
            it.error(R.drawable.no_image)
        }
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(text = assetName, style = MaterialTheme.typography.titleLarge)
            Text(
                text = assetId,
                modifier = modifier.padding(top = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }


}

@Composable
fun DetailsSecondPart(
    symbol: String,
    horus: String,
    day: String,
    month: String,
    price: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        DetailLabel(label = "Data Symbole", value = symbol)
        DetailLabel(label = "volume in 1 hours", value = horus)
        DetailLabel(label = "volume in 1day ", value = day)
        DetailLabel(label = "volume in 1 month", value = month)
        DetailLabel(label = "price", value = price)
    }
}

@Composable
fun DetailRatePart(time: String, rate: String, quote: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier) {
        Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
            DetailLabel(label = "Time", value = time)
            DetailLabel(label = "price", value = "$rate $quote")
        }
    }

}

@Composable
fun DetailLabel(label: String, value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$label:",
            modifier = modifier.padding(4.dp),
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            modifier = modifier.padding(4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Preview
@Composable
fun DetailLablePreview() {
    DetailLabel("ID", "BTC")
}

@Preview
@Composable
fun ContentSectionPreview() {
    CryptoPricesTheme {
//        ContentSection(asset = AssetDetail())
    }
}