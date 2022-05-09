package com.crownedjester.soft.whackamolegame.domain

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

    val record: Flow<Int>

    suspend fun setRecord(record: Int)

}