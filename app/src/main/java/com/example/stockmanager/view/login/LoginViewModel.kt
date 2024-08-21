package com.example.stockmanager.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.model.LoginRequest
import com.example.stockmanager.data.model.LoginResponse
import com.example.stockmanager.data.repository.LoginRepository
import com.example.stockmanager.util.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val sharedPreferencesUtil: SharedPreferencesUtil
) : ViewModel() {

    private val _loginSuccess = MutableLiveData<Boolean>()
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> = _loginError

    fun login(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                loginRepository.login(LoginRequest(email, password))
            }.onSuccess { response ->
                handleResponse(response)
            }.onFailure { exception ->
                handleFailure(exception)
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<LoginResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { loginResponse ->
                if (loginResponse.statusCode == 200) {
                    _loginSuccess.value = true
                    _loginError.value = null
                    val token = loginResponse.result.token
                    sharedPreferencesUtil.saveToken(token)
                } else {
                    _loginSuccess.value = false
                    _loginError.value = loginResponse.message
                }
            } ?: run {
                _loginSuccess.value = false
                _loginError.value = "Invalid response body"
            }
        } else {
            _loginSuccess.value = false
            _loginError.value = "Login failed with status code: ${response.code()}"
        }
    }

    private fun handleFailure(exception: Throwable) {
        _loginSuccess.value = false
        _loginError.value = exception.message ?: "An unknown error occurred"
    }
}
