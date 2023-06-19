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
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.data.DataState
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import com.rezapour.cryptoprices.view.view_models.AssetListViewModel
import kotlinx.coroutines.CoroutineScope

//TODO: in compose instead of Pass by Value() we can use CompositionLocal or Static CompositionLocal.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(
    viewModel: AssetListViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateToDetail: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopBar(
                viewModel = viewModel,
                showSearchBar = viewModel.showSearchBar,
                favoriteState = viewModel.favoriteState,
                onSearchClicked = { viewModel.updateSearchState(true) },
                onFavoriteClicked = {
                    viewModel.updateFavoriteState(!viewModel.favoriteState)
                },
                onBackedClicked = {
                    viewModel.updateSearchState(false)
                    viewModel.updateFavoriteState(false)
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
        if (viewModel.showSearchBar || viewModel.favoriteState) {
            Content(
                viewModel,
                onNavigateToDetail,
                Modifier.padding(padding)
            )
        } else {
            PaginationContent(
                viewModel,
                onNavigateToDetail,
                snackbarHostState,
                scope,
                Modifier.padding(padding)
            )
        }
    }
}

@Composable
fun PaginationContent(
    viewModel: AssetListViewModel,
    onItemClicked: (String) -> Unit,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier
) {
    val assetList = viewModel.assetPagingFlow.collectAsLazyPagingItems()
    AssetList(modifier, onErrorLabel = {
        if (assetList.loadState.refresh is LoadState.Error) {
            ErrorLabel()
            SnackBarItem(
                snackBarHostState,
                coroutineScope,
                stringResource(id = R.string.error_default_message),
                stringResource(R.string.retry)
            ) { assetList.retry() }
        }
    }, list = {
        PaginationList(assetList, { asset, checkState ->
            if (checkState) viewModel.addFavorite(asset) else viewModel.deleteFavorite(asset.assetId)
        }, onItemClicked)
    })
}

@Composable
fun Content(
    viewModel: AssetListViewModel,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (val state = viewModel.dataState.collectAsState().value) {
        is DataState.Error -> {

        }

        DataState.Loading -> LoadingInList(modifier)
        is DataState.Success -> {
            val assets = remember { state.data.toMutableStateList() }
            AssetList(modifier = modifier) {

                NormalList(
                    assets,
                    { asset, checkState ->
                        if (checkState) viewModel.addFavorite(asset) else {
                            viewModel.deleteFavorite(asset.assetId)
                            assets.removeIf { it.asset == asset }
                        }

                    },
                    onItemClicked
                )
            }
        }

        is DataState.EmptyList -> TextMessage(message = R.string.empty_list, modifier)
    }
}

@Composable
fun AssetList(
    modifier: Modifier = Modifier,
    onErrorLabel: @Composable () -> Unit = {},
    list: LazyListScope.() -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        onErrorLabel()
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            list()
        }
    }

}


fun LazyListScope.NormalList(
    assetItems: List<AssetItem>,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
) {
    items(items = assetItems, key = { assetItem -> assetItem.asset.assetId }) { assetItem ->
        AssetItemState(assetItem = assetItem, onFavoriteClicked, onItemClicked)
    }
    if (assetItems.size == 0)
        item { TextMessage(message = R.string.empty_list) }
}


fun LazyListScope.PaginationList(
    assetItems: LazyPagingItems<AssetItem>,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
) {
    items(
        count = assetItems.itemCount,
        key = assetItems.itemKey(),
        contentType = assetItems.itemContentType(
        )
    ) { index ->
        val item = assetItems[index]
        item?.let { AssetItemState(assetItem = it, onFavoriteClicked, onItemClicked) }
    }

    item {
        if (assetItems.loadState.refresh is LoadState.Loading) {
            LoadingInList()
        }
    }
}


@Composable
fun AssetItemState(
    assetItem: AssetItem,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var checkState by rememberSaveable { mutableStateOf(assetItem.favorite) }

    AssetItem(assetItem.asset, checkState, {
        checkState = !checkState
        onFavoriteClicked(assetItem.asset, checkState)
    }, onItemClicked, modifier)
}


@OptIn(ExperimentalGlideComposeApi::class)
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
            GlideImage(
                model = Constance.getImageUrl(asset.idIcon?.replace("-", "")),
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
fun TopBar(
    viewModel: AssetListViewModel,
    showSearchBar: Boolean = false,
    favoriteState: Boolean = false,
    onFavoriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onBackedClicked: () -> Unit
) {
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("") }
    if (showSearchBar)
        SearchBar(
            onBackedClicked,
            text = text,
            { newText -> text = newText },
            { text ->
                if (text.isNotEmpty()) viewModel.search(text) else ToastMessage(
                    context,
                    message = R.string.empty_text
                )
            })
    else
        TopAppBar(title = { Text(text = stringResource(R.string.cryptocurrencies)) }, actions = {
            if (favoriteState) viewModel.getFavorite()
            TopBarDefault(
                favoriteState,
                onFavoriteClicked,
                onSearchClicked
            )
        })

}

@Composable
fun TopBarDefault(
    favoriteState: Boolean,
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
                tint = if (favoriteState) Color.Red else Color.Gray
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
