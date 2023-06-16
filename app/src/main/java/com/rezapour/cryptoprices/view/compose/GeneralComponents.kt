package com.rezapour.cryptoprices.view.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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