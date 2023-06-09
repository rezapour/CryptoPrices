package com.rezapour.cryptoprices.view.compose

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.rezapour.cryptoprices.R
import com.rezapour.cryptoprices.data.Constance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun SnackBarItem(
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope,
    message: String,
    actionLabel: String? = null,
    onActionClicked: (() -> Unit)? = null,
) {
    scope.launch {

        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabel,
            duration = SnackbarDuration.Indefinite
        )
        when (result) {
            SnackbarResult.Dismissed -> {
            }

            SnackbarResult.ActionPerformed -> if (onActionClicked != null) {
                onActionClicked()
            }
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }

}

@Composable
fun LoadingInList(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }

}

@Composable
fun TextMessage(@StringRes message: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(id = message), style = MaterialTheme.typography.bodyLarge)
    }

}

@Composable
fun ErrorLabel(onlClick: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.this_data_is_outdated_please_refresh),
            modifier = Modifier
                .padding(4.dp)
                .clickable { onlClick() },
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

fun ToastMessage(context: Context, @StringRes message: Int) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageLoader(modifier: Modifier = Modifier, imageUrl: String) {
    GlideImage(
        model = imageUrl,
        contentDescription = "",
        contentScale = ContentScale.Crop,
        modifier = modifier

    ) {
        it.error(R.drawable.no_image)
    }
}