package com.rezapour.cryptoprices.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import com.rezapour.cryptoprices.view.view_models.AssetListViewModel
import kotlinx.coroutines.CoroutineScope


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(
    viewModel: AssetListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    OnNavigateToDetail: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = { TopBar(viewModel) },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        Content(viewModel, snackbarHostState, scope, Modifier.padding(padding))
    }

}

//TODO make a cleaner code
@Composable
fun Content(
    viewModel: AssetListViewModel,
    snackbarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    when (val state = viewModel.dataState.collectAsState().value) {
        is DataState.DefaultError -> {
            SnackBarItem(
                snackbarHostState,
                coroutineScope,
                stringResource(R.string.error_default_message),
                stringResource(R.string.retry)
            ) { viewModel.loadData() }

            AssetList(
                assetItems = state.data ?: emptyList(),
                { asset, checkState ->
                    if (checkState) viewModel.addFavorite(asset) else viewModel.deleteFavorite(asset.assetId)
                }, modifier = modifier
            )
        }

        is DataState.Error -> {
            SnackBarItem(
                snackbarHostState,
                coroutineScope,
                stringResource(id = state.message),
                stringResource(R.string.retry)
            ) { viewModel.loadData() }

            AssetList(
                assetItems = state.data,
                { asset, checkState ->
                    if (checkState) viewModel.addFavorite(asset) else viewModel.deleteFavorite(asset.assetId)
                }, modifier = modifier
            )
        }

        DataState.Loading -> Loading(modifier)
        is DataState.Success -> AssetList(
            assetItems = state.data,
            { asset, checkState ->
                if (checkState) viewModel.addFavorite(asset) else viewModel.deleteFavorite(asset.assetId)
            }, modifier = modifier
        )
    }
}

@Composable
fun AssetList(
    assetItems: List<AssetItem>,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    useLocalData: Boolean = true,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (useLocalData)
            Surface(color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "This data is outdated please refresh.",
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }


        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = assetItems, key = { assetItem -> assetItem.asset.assetId }) { assetItem ->
                AssetItemState(assetItem = assetItem, onFavoriteClicked)
            }
        }
    }

}

@Composable
fun AssetItemState(
    assetItem: AssetItem,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkState by rememberSaveable { mutableStateOf(assetItem.favorite) }

    AssetItem(assetItem.asset, checkState, {
        checkState = !checkState
        onFavoriteClicked(assetItem.asset, checkState)
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
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GlideImage(
                model = Constance.getImageUrl(asset.idIcon?.replace("-","")),
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

//TODO load data should change to chash not load again
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: AssetListViewModel) {
    var showSearchBar by rememberSaveable { mutableStateOf(false) }
    var favoriteState by rememberSaveable { mutableStateOf(false) }
    var text by rememberSaveable { mutableStateOf("") }
    if (showSearchBar)
        SearchBar(
            {
                showSearchBar = false
                viewModel.loadData()
            },
            text = text,
            { newText -> text = newText },
            { text -> viewModel.search(text) })
    else
        TopAppBar(title = { Text(text = stringResource(R.string.cryptocurrencies)) }, actions = {
            TopBarDefault(
                {   //TODO only fetch data
                    favoriteState = !favoriteState
                    if (favoriteState) viewModel.getFavorite() else viewModel.loadData()
                },
                { showSearchBar = true })
        })

}

@Composable
fun TopBarDefault(
    onFavoriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        IconButton(onClick = onFavoriteClicked, modifier = Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        }

        IconButton(onClick = onSearchClicked, modifier = Modifier.padding(0.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onBackedClicked: () -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = text,
        onValueChange = onTextChange,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                Modifier.clickable { onBackedClicked() })
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                Modifier.clickable { onSearchClicked(text) })
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        placeholder = {
            Text("Search")
        },
        maxLines = 1,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}
