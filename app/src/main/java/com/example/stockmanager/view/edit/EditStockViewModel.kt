package com.example.stockmanager.view.edit

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
class EditStockViewModel @Inject constructor(
    private val stockRepository: StockRepository
) : ViewModel() {

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isStockEdited = MutableStateFlow(false)
    val isStockEdited: StateFlow<Boolean> = _isStockEdited.asStateFlow()

    fun editStock(id: Int, request: StockRequest) {
        viewModelScope.launch {
            val response = stockRepository.updateStocks(
                id,
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
                        _isStockEdited.value = true
                    } else {
                        _error.value = it.message
                        _isStockEdited.value = false
                    }
                }
            } else {
                response.body()?.let {
                    _error.value = it.message
                    _isStockEdited.value = false
                }
            }
        }
    }

}