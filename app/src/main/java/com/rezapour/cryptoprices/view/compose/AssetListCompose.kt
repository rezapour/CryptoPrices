package com.rezapour.cryptoprices.view.compose

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.view.asset_list.AssetListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rezapour.cryptoprices.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(viewModel: AssetListViewModel = viewModel(), modifier: Modifier = Modifier) {

    Scaffold(topBar = { TopBar(viewModel) }) { padding ->
        Content(viewModel, Modifier.padding(padding))
    }

}

@Composable
fun Content(viewModel: AssetListViewModel, modifier: Modifier = Modifier) {
    when (val state = viewModel.dataState.collectAsState().value) {
        is DataState.DefaultError -> Log.d("REZAAPP", "DefaultError")
        is DataState.Error -> Log.d("REZAAPP", stringResource(state.message))
        DataState.Loading -> Log.d("REZAAPP", "Loading")
        is DataState.Success -> AssetList(
            assets = state.data,
            { asset, checkState ->
                if (checkState) viewModel.addFavorite(asset) else viewModel.deleteFavorite(asset.assetId)
            })
    }
}

@Composable
fun AssetList(
    assets: List<Asset>,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(items = assets, key = { asset -> asset.assetId }) { asset ->
            AssetItemState(asset = asset, onFavoriteClicked)
        }
    }
}

@Composable
fun AssetItemState(
    asset: Asset,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkState by rememberSaveable { mutableStateOf(asset.favorite) }

    AssetItem(asset, checkState, {
        checkState = !checkState
        onFavoriteClicked(asset, checkState)
    })
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AssetItem(
    asset: Asset,
    checked: Boolean,
    onCheckedChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        GlideImage(
            model = asset.idIcon,
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
            Text(text = asset.name)
            Text(text = asset.assetId, modifier = modifier.padding(top = 4.dp))
        }

        IconButton(onClick = onCheckedChanged, modifier = Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (checked) Color.Red else Color.Black
            )
        }
    }
}

@Composable
fun TopBar(viewModel: AssetListViewModel) {
    var showSearchBar by rememberSaveable { mutableStateOf(false) }
    var favoriteState by rememberSaveable { mutableStateOf(false) }
    if (showSearchBar)
        SearchBar({ showSearchBar = false })
    else
        TopBarDefault(
            {   //TODO only fetch data
                favoriteState = !favoriteState
                if (favoriteState) viewModel.getFavorite() else viewModel.loadData()
            },
            { showSearchBar = true })

}

@Composable
fun TopBarDefault(
    onFavoriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        IconButton(onClick = onFavoriteClicked, modifier = Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
        }

        IconButton(onClick = onSearchClicked, modifier = Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.White
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(onBackedClicked: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = onBackedClicked, modifier = Modifier.padding(0.dp)) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = Color.White
            )
        }
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }, colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.surface
            ), placeholder = {
                Text("Search")
            }, modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

}

@Preview
@Composable
fun AssetItemPreview() {
    AssetItem(
        Asset(
            "BTC",
            "BitCoin",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/4/46/Bitcoin.svg/1200px-Bitcoin.svg.png"
        ), false, {}
    )
}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar({})
}




