package com.rezapour.cryptoprices.view.compose

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.AssetDetail
import com.rezapour.cryptoprices.view.view_models.AssetDetailViewModel

@Composable
fun AssetDetailScreen(viewModel: AssetDetailViewModel = hiltViewModel()) {
    Log.d("REZAPOUR", "Start")
    viewModel.loadAssetDetail("BTC")
    Screan(viewModel)
}

@Composable
fun Screan(viewModel: AssetDetailViewModel) {
    when (val state = viewModel.dataState.collectAsState().value) {
        is DataState.DefaultError -> {
            Log.d("REZAPOUR", "error")
        }

        is DataState.Error -> {
            Log.d("REZAPOUR", "error")
        }

        DataState.Loading -> {
            Log.d("REZAPOUR", "loading")
        }

        is DataState.Success -> ContentSection(asset = state.data)
    }
}

@Composable
fun ContentSection(asset: AssetDetail, modifier: Modifier = Modifier) {
    Log.d("REZAPOUR", "ss")
    Column(modifier = modifier) {
        with(asset) {
            DetailFirstPart(imageUrl = "", assetId = assetId, assetName = name)
            DetailsSecondPart(
                symbol = dataSymbolsCount.toString(),
                horus = volume1hrsUsd.toString(),
                day = volume1dayUsd.toString(),
                month = volume1mthUsd.toString(),
                price = priceUsd.toString()
            )
            DetailRatePart(time = exchnageTime, rate = rate.toString(), quote = assetIdQuote)
        }
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
        //TODO glide error icon
        GlideImage(
            model = imageUrl,
            contentDescription = "Hello",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .padding(4.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(text = assetName)
            Text(text = assetId, modifier = modifier.padding(top = 4.dp))
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
    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        DetailLabel(label = "Time", value = time)
        DetailLabel(label = "price", value = "$rate $quote")
    }
}

@Composable
fun DetailLabel(label: String, value: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "$label:")
        Text(text = value)
    }
}

@Preview
@Composable
fun DetailLablePreview() {
    DetailLabel("ID", "BTC")
}