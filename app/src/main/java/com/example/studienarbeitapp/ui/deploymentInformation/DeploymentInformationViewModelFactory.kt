package com.example.studienarbeitapp.ui.deploymentInformation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.DeplyomentInformationService

class DeploymentInformationViewModelFactory (private val deployInformationService: DeplyomentInformationService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(DeploymentInformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeploymentInformationViewModel(deployInformationService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}