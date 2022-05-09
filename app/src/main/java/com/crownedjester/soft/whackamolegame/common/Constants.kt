package com.crownedjester.soft.whackamolegame.common

import androidx.datastore.preferences.core.intPreferencesKey

object Constants {
    const val DATASTORE_NAME = "whack_a_mole_datastore"

    private const val RECORD_KEY_NAME = "result_key"
    val RECORD_KEY = intPreferencesKey(RECORD_KEY_NAME)

}