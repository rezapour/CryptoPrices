package com.rezapour.cryptoprices.data.repository.impl

import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.model.Asset
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDao: FavoriteDao,
    private val dataBaseMapper: DataBaseMapper
) :
    FavoriteRepository {
    override suspend fun getFavorite(): List<Asset> =
        dataBaseMapper.favoriteDatabaseEntityListToAssetList(favoriteDao.getFavorite())


    override suspend fun addFavorite(asset: Asset) {
        favoriteDao.insertFavorite(dataBaseMapper.assetToFavoriteDatabaseEntity(asset))
    }

    override suspend fun deleteFavorite(assetId: String) {
        favoriteDao.deleteFavorite(assetId)
    }

    override suspend fun getFavoriteId(): Set<String> = favoriteDao.getFavoriteAssetIds().toSet()

}