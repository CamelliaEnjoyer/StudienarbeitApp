package com.example.studienarbeitapp.ui.deploymentInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentInformationModel
import com.example.studienarbeitapp.services.DeplyomentInformationService

class DeploymentInformationViewModel(private val deployInformationService: DeplyomentInformationService) : ViewModel() {

    val deploymentInfo = MutableLiveData<ResponseDeploymentInformationModel>()

    fun getDeploymentInfoFromService() {
        if(deploymentInfo.value == null){
            deployInformationService.fetchDeplyomentInformation(
                onSuccess = { deploymentTimeResponse ->
                    // Update LiveData with the fetched user data
                    deploymentInfo.value = deploymentTimeResponse
                },
                onError = {
                    // Handle error
                }
            )
        }
    }
}