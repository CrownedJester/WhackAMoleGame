package com.crownedjester.soft.whackamolegame.presentation.result_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crownedjester.soft.whackamolegame.domain.DataStoreRepository
import com.crownedjester.soft.whackamolegame.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val dataStoreManager: DataStoreRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _gameResult = MutableStateFlow(0)
    val gameResult: StateFlow<Int> get() = _gameResult

    private var _gameRecord = MutableStateFlow(0)
    val gameRecord: StateFlow<Int> get() = _gameRecord

    init {
        viewModelScope.launch(Dispatchers.Main) {
            savedStateHandle.get<Int>(Screen.RESULT_ARGUMENT_NAME)?.let {
                _gameResult.emit(it)
            }
        }

        loadGameRecord()

        onGameFinish()
    }

    private fun loadGameRecord() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreManager.record.collectLatest {
                _gameRecord.emit(it)
                Log.i("ResultViewModel", "record = $it")
            }
        }
    }

    private fun onGameFinish() {
        viewModelScope.launch(Dispatchers.Main) {
            if (gameResult.value > gameRecord.value) {
                dataStoreManager.setRecord(gameResult.value)
                Log.i("ResultViewModel", "Record was updated")
            }
        }
    }

}