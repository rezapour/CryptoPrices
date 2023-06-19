package com.rezapour.cryptoprices.data.prefrence

interface SortState {
    fun getSearchState(): Boolean

    fun setSearchState(searchState: Boolean)

    fun getFavoriteState(): Boolean

    fun setFavoriteState(favoriteState: Boolean)

}