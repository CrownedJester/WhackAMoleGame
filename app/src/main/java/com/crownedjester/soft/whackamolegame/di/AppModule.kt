package com.crownedjester.soft.whackamolegame.di

import android.content.Context
import com.crownedjester.soft.whackamolegame.domain.DataStoreManager
import com.crownedjester.soft.whackamolegame.domain.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStoreRepository =
        DataStoreManager(context)

}