package com.example.studienarbeitapp.ui.deploymentTime

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.studienarbeitapp.services.DeploymentTimeService

class DeploymentTimeViewModelFactory(private val deploymentTimeService: DeploymentTimeService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(DeploymentTimeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeploymentTimeViewModel(deploymentTimeService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}