package com.rezapour.cryptoprices.data.repository.impl

import com.rezapour.cryptoprices.data.database.mapper.DataBaseMapper
import com.rezapour.cryptoprices.data.database.room.dao.FavoriteDao
import com.rezapour.cryptoprices.data.repository.FavoriteRepository
import com.rezapour.cryptoprices.model.Asset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/*TODO: For improvement we can have a favorite use-case for putting all the logic regarding the favorite
        like putting limitation for favorite and etc.
 */

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