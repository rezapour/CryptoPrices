package com.rezapour.cryptoprices.view.compose

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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