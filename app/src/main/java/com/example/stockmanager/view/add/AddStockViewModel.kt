package com.example.stockmanager.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.model.StockRequest
import com.example.stockmanager.data.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddStockViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isStockAdded = MutableStateFlow(false)
    val isStockAdded: StateFlow<Boolean> = _isStockAdded.asStateFlow()

    fun addStock(request: StockRequest) {
        viewModelScope.launch {
            val response = stockRepository.createStocks(
                StockRequest(
                    request.name,
                    request.purchase_date,
                    request.image
                )
            )
            if (response.isSuccessful) {
                response.body()?.let {
                    val statusCode = it.statusCode
                    if (statusCode == 201) {
                        _error.value = null
                        _isStockAdded.value = true
                    } else {
                        _error.value = it.message
                        _isStockAdded.value = false
                    }
                }
            } else {
                response.body()?.let {
                    _error.value = it.message
                    _isStockAdded.value = false
                }
            }
        }
    }

}