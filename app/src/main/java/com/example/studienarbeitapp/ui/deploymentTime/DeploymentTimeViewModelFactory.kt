package com.example.studienarbeitapp.ui.deploymentTime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.DeploymentTimeService

/**
 * Factory class responsible for creating instances of DeploymentTimeViewModel.
 */
class DeploymentTimeViewModelFactory(private val deploymentTimeService: DeploymentTimeService) :
    ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified ViewModel.
     *
     * @param modelClass The class of the ViewModel to create.
     * @param extras Creation extras.
     * @return The created ViewModel instance.
     * @throws IllegalArgumentException if the provided ViewModel class is unknown.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(DeploymentTimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeploymentTimeViewModel(deploymentTimeService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}