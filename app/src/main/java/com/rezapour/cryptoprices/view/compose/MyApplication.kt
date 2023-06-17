package com.rezapour.cryptoprices.view.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyApplication(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "assetList"
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("assetList") {
            AssetListScreen() {assetid-> navController.navigate("detail/$assetid")}
        }

        composable("detail/{assetId}") { backStackEntry ->
            AssetDetailScreen(
                assetId = backStackEntry.arguments?.getString(
                    "assetId"
                ), backButton = {
                    navController.popBackStack()
                }
            )
        }
    }
}
