package com.example.studienarbeitapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.LoginService

class LoginViewModelFactory(private val loginService: LoginService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}