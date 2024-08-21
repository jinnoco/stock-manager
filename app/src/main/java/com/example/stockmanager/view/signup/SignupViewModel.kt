package com.example.stockmanager.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.model.SignUpRequest
import com.example.stockmanager.data.model.SignUpResponse
import com.example.stockmanager.data.repository.SignupRepository
import com.example.stockmanager.util.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository,
    private val sharedPreferencesUtil: SharedPreferencesUtil
) : ViewModel() {

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> = _signupSuccess

    private val _signupError = MutableLiveData<String?>()
    val signupError: LiveData<String?> = _signupError

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            runCatching {
                signupRepository.signup(SignUpRequest(email, password))
            }.onSuccess { response ->
                handleResponse(response)
            }.onFailure { exception ->
                handleFailure(exception)
            }
        }
    }

    private fun handleResponse(response: retrofit2.Response<SignUpResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { signUpResponse ->
                if (signUpResponse.statusCode == 201) {
                    _signupSuccess.value = true
                    _signupError.value = null
                    val token = signUpResponse.result.token
                    sharedPreferencesUtil.saveToken(token)
                } else {
                    _signupSuccess.value = false
                    _signupError.value = signUpResponse.message
                }
            } ?: run {
                _signupSuccess.value = false
                _signupError.value = "Invalid response body"
            }
        } else {
            _signupSuccess.value = false
            _signupError.value = "SignUp failed with status code: ${response.code()}"
        }
    }

    private fun handleFailure(exception: Throwable) {
        _signupSuccess.value = false
        _signupError.value = exception.message ?: "An unknown error occurred"
    }

}
