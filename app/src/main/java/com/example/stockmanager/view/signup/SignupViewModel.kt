package com.example.stockmanager.view.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.model.SignUpRequest
import com.example.stockmanager.data.repository.SignupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupRepository: SignupRepository
) : ViewModel() {

    private val _signupSuccess = MutableLiveData<Boolean>()
    val signupSuccess: LiveData<Boolean> = _signupSuccess

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = signupRepository.signup(SignUpRequest(email, password))
                if (response.isSuccessful) {
                    val signUpResponse = response.body()
                    signUpResponse.let { res ->
                        _signupSuccess.value = true
                    }
                } else {
                    _signupSuccess.value = false
                }
            } catch (e: Exception) {
                _signupSuccess.value = false
            }
        }
    }

}