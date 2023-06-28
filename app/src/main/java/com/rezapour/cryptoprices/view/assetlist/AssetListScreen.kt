package com.rezapour.cryptoprices.view.assetlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.model.Asset
import com.rezapour.cryptoprices.model.AssetItem
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.view.compose.AssetItem
import com.rezapour.cryptoprices.view.compose.ErrorLabel
import com.rezapour.cryptoprices.view.compose.Loading
import com.rezapour.cryptoprices.view.compose.TextMessage
import com.rezapour.cryptoprices.view.compose.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(
    modifier: Modifier = Modifier,
    viewModel: AssetListViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        topBar = {
            TopBar(
                viewModel = viewModel,
                uiState = uiState,
                showSearchBar = viewModel.showSearchBar,
                favoriteState = viewModel.favoriteState,
                onSearchClicked = {
                    viewModel.updateSearchState(true)
                    viewModel.cleanList()
                    if (viewModel.favoriteState) viewModel.updateFavoriteState(!viewModel.favoriteState)
                },
                onFavoriteClicked = {
                    if (viewModel.favoriteState) {
                        viewModel.loadData()
                    } else {
                        viewModel.getFavorite()
                    }
                    viewModel.updateFavoriteState(!viewModel.favoriteState)
                },
                onBackedClicked = {
                    viewModel.loadData()
                    viewModel.updateSearchState(false)
                    viewModel.updateFavoriteState(false)
                }
            )
        }) { padding ->
        Content(
            uiState = uiState,
            viewModel = viewModel,
            onItemClicked = onNavigateToDetail,
            onFavoriteClicked = { asset, checked ->
                if (checked) viewModel.addFavorite(asset) else
                    viewModel.deleteFavorite(asset.assetId)
            },
            modifier = modifier.padding(padding)
        )
    }
}

@Composable
fun Content(
    modifier: Modifier = Modifier,
    uiState: UiState<List<AssetItem>>,
    viewModel: AssetListViewModel,
    onItemClicked: (String) -> Unit,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
) {
    when (uiState) {
        is UiState.Error -> {
            ContentItemState(
                modifier = modifier,
                assetItems = uiState.data,
                showError = true,
                topFavoriteState = viewModel.favoriteState,
                onFavoriteItemClicked = onFavoriteClicked,
                onItemClicked = onItemClicked,
                onErrorLabelClicked = { viewModel.loadData() }
            )
        }

        UiState.Loading -> {
            Loading()
        }

        is UiState.Success -> {
            ContentItemState(
                modifier = modifier,
                assetItems = uiState.data,
                showError = false,
                topFavoriteState = viewModel.favoriteState,
                onFavoriteItemClicked = onFavoriteClicked,
                onItemClicked = onItemClicked
            )
        }
    }
}

@Composable
fun ContentItemState(
    modifier: Modifier,
    assetItems: List<AssetItem>,
    showError: Boolean = false,
    topFavoriteState: Boolean,
    onFavoriteItemClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
    onErrorLabelClicked: () -> Unit = {}
) {
    var assetItemState = remember { mutableStateListOf<AssetItem>() }
    assetItemState.clear()
    assetItemState.addAll(assetItems)
    ContentItem(
        modifier = modifier,
        assetItems = assetItemState,
        showError = showError,
        onFavoriteClicked = { asset, checked ->
            onFavoriteItemClicked(asset, checked)
            if (topFavoriteState && !checked)
                assetItemState.removeIf { assetItem -> assetItem.asset.assetId == asset.assetId }
        },
        onItemClicked = onItemClicked,
        onErrorLabelClicked = onErrorLabelClicked
    )
}

@Composable
fun ContentItem(
    modifier: Modifier,
    assetItems: List<AssetItem>,
    showError: Boolean = false,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
    onErrorLabelClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showError)
            ErrorLabel(onlClick = onErrorLabelClicked)
        if (assetItems.isNotEmpty())
            AssetList(
                assetItems = assetItems,
                onFavoriteClicked = onFavoriteClicked,
                onItemClicked = onItemClicked
            )
        else
            TextMessage(message = R.string.empty_list)
    }

}

@Composable
fun AssetList(
    modifier: Modifier = Modifier,
    assetItems: List<AssetItem>,
    onFavoriteClicked: (Asset, Boolean) -> Unit,
    onItemClicked: (String) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = assetItems, key = { assetItem -> assetItem.asset.assetId }) { assetItem ->
            AssetItemState(assetItem = assetItem, onFavoriteClicked, onItemClicked)
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




