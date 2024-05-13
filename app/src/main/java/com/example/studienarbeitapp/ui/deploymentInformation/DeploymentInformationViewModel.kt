package com.example.studienarbeitapp.ui.deploymentInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.request.RequestDeploymentInformationModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentInformationModel
import com.example.studienarbeitapp.services.DeplyomentInformationService

class DeploymentInformationViewModel(private val deployInformationService: DeplyomentInformationService) : ViewModel() {

    val deploymentInfoResponse = MutableLiveData<ResponseDeploymentInformationModel>()
    val deploymentInfoRequest = MutableLiveData<RequestDeploymentInformationModel>()

    fun getDeploymentInfoFromService() {
        if(deploymentInfoResponse.value == null){
            deployInformationService.fetchDeplyomentInformation(
                onSuccess = { deploymentInfoResponse ->
                    // Update LiveData with the fetched user data
                    this.deploymentInfoResponse.value = deploymentInfoResponse
                },
                onError = {
                    // Handle error
                }
            )
        }
    }
}