package com.example.stockmanager.view.stocklist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.model.StockResponseData
import com.example.stockmanager.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StockListViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _stockList = MutableStateFlow<List<StockResponseData>>(emptyList())
    val stockList: StateFlow<List<StockResponseData>> = _stockList.asStateFlow()

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        fetchStocks()
    }

    private fun fetchStocks() {
        viewModelScope.launch {
            val response = stockRepository.getStocks()
            if (response.isSuccessful) {
                response.body()?.let {
                    _stockList.value = it.result.data
                }
            } else {
                response.body()?.let {
                    _error.value = it.message
                }
            }
        }
    }
}
