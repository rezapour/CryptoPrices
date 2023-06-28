package com.rezapour.cryptoprices.view.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.model.AssetItem
import com.rezapour.cryptoprices.util.UiState
import com.rezapour.cryptoprices.view.assetlist.AssetListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: AssetListViewModel,
    uiState: UiState<List<AssetItem>>,
    showSearchBar: Boolean = false,
    favoriteState: Boolean = false,
    onFavoriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    onBackedClicked: () -> Unit
) {
    val context = LocalContext.current
    var text by rememberSaveable { mutableStateOf("") }
    var enableState by rememberSaveable { mutableStateOf(false) }

    enableState = !(uiState is UiState.Loading)
    if (showSearchBar)
        SearchBar(
            onBackedClicked = onBackedClicked,
            text = text,
            onTextChange = { newText ->
                text = newText
                viewModel.search(newText)
            },
            onSearchClicked = { text ->
                if (text.isNotEmpty()) viewModel.search(text) else ToastMessage(
                    context,
                    message = R.string.empty_text
                )
            })
    else
        TopAppBar(
            title = { Text(text = stringResource(R.string.cryptocurrencies)) },
            actions = {
                TopBarDefault(
                    favoriteState,
                    enableState,
                    onFavoriteClicked,
                    onSearchClicked
                )
            })
}


@Composable
fun TopBarDefault(
    favoriteState: Boolean,
    enableState: Boolean,
    onFavoriteClicked: () -> Unit,
    onSearchClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        IconButton(
            onClick = onFavoriteClicked,
            enabled = enableState,
            modifier = Modifier.padding(0.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (favoriteState) Color.Red else Color.Gray
            )
        }

        IconButton(
            onClick = onSearchClicked,
            enabled = enableState,
            modifier = Modifier.padding(0.dp)
        ) {
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
            .heightIn(min = 56.dp),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearchClicked(text) })
    )
}
