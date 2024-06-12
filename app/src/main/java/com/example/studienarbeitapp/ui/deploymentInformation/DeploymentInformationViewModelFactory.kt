package com.example.studienarbeitapp.ui.deploymentInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.DeploymentInformationService

/**
 * Factory class for creating DeploymentInformationViewModel instances.
 *
 * @param deployInformationService The service used for fetching deployment information.
 */
class DeploymentInformationViewModelFactory(private val deployInformationService: DeploymentInformationService) :
    ViewModelProvider.Factory {

    /**
     * Creates a new instance of the specified ViewModel class.
     *
     * @param modelClass The class of the ViewModel to create.
     * @param extras Additional parameters for ViewModel creation.
     * @return A new instance of the specified ViewModel class.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(DeploymentInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeploymentInformationViewModel(deployInformationService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
