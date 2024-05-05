package com.example.studienarbeitapp.ui.deploymentTime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.DeploymentTimeModel
import com.example.studienarbeitapp.services.DeploymentTimeService

class DeploymentTimeViewModel(private val deploymentTimeService: DeploymentTimeService) : ViewModel() {

    val deploymentTime = MutableLiveData<DeploymentTimeModel>()

    fun getDeploymentTimeFromService() {
        deploymentTimeService.fetchDeplyomentTime(
            onSuccess = { deploymentTimeResponse ->
                // Update LiveData with the fetched user data
                deploymentTime.value = deploymentTimeResponse
            },
            onError = {
                // Handle error
            }
        )
    }
}