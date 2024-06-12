package com.example.studienarbeitapp.ui.deploymentInformation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentInformationModel
import com.example.studienarbeitapp.services.DeploymentInformationService

/**
 * ViewModel responsible for managing deployment information.
 *
 * @param deployInformationService The service used for fetching deployment information.
 */
class DeploymentInformationViewModel(private val deployInformationService: DeploymentInformationService) :
    ViewModel() {

    /**
     * LiveData for deployment information response.
     */
    val deploymentInfoResponse = MutableLiveData<ResponseDeploymentInformationModel>()

    /**
     * Retrieves deployment information from the service.
     */
    fun getDeploymentInfoFromService() {
        deployInformationService.fetchDeploymentInformation(
            onSuccess = { deploymentInfoResponse ->
                // Update LiveData with the fetched deployment information
                this.deploymentInfoResponse.value = deploymentInfoResponse
            },
            onError = {
                // Handle error
            }
        )
    }
}
