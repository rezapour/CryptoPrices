package com.rezapour.cryptoprices.data.prefrence

import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject

class SortStateImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : SortState {

    companion object {
        const val SEARCH_STATE = "SearchState"
        const val FAVORITE_STATE = "FavoriteState"
    }

    override fun getSearchState(): Boolean {
        return sharedPreferences.getBoolean(SEARCH_STATE, false)
    }

    override fun setSearchState(searchState: Boolean) {
        Log.d("APPREZA","Search")
        editor.putBoolean(SEARCH_STATE, searchState)
        editor.commit()
    }

    override fun getFavoriteState(): Boolean {
        return sharedPreferences.getBoolean(FAVORITE_STATE, false)
    }

    override fun setFavoriteState(favoriteState: Boolean) {
        editor.putBoolean(FAVORITE_STATE, favoriteState)
        editor.commit()
    }

}