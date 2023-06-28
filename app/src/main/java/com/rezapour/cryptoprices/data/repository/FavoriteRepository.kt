package com.rezapour.cryptoprices.data.repository

import com.rezapour.cryptoprices.model.Asset
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {

    suspend fun getFavorite(): List<Asset>

    suspend fun addFavorite(asset: Asset)

    suspend fun deleteFavorite(assetId: String)

    suspend fun getFavoriteId(): Set<String>
}