package com.example.studienarbeitapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.LoginService

/**
 * Factory class for creating instances of {@link LoginViewModel}.
 */
class LoginViewModelFactory(private val loginService: LoginService) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given {@code Class}, using the provided {@code CreationExtras}.
     *
     * @param modelClass A {@code Class} whose instance is requested.
     * @param extras Additional information used to create the {@link ViewModel}.
     * @param <T> The type parameter for the {@link ViewModel}.
     * @return A newly created {@link ViewModel}.
     * @throws IllegalArgumentException If the {@code modelClass} is not assignable from {@link LoginViewModel}.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(loginService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
