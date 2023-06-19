package com.rezapour.cryptoprices.di.prefrece

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.rezapour.cryptoprices.data.Constance
import com.rezapour.cryptoprices.data.prefrence.SortState
import com.rezapour.cryptoprices.data.prefrence.SortStateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class prefrenceModule {

    @Singleton
    @Provides
    fun sharedPreferencesProvider(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constance.PREFERENCE_NAME, MODE_PRIVATE)
    }


    @Singleton
    @Provides
    fun editorProvider(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    @Singleton
    @Provides
    fun sortStateProvider(
        sharedPreferences: SharedPreferences,
        editor: SharedPreferences.Editor
    ): SortState {
        return SortStateImpl(sharedPreferences, editor)
    }
}