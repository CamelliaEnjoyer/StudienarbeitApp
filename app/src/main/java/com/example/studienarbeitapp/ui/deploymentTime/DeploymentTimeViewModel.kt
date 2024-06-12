package com.example.studienarbeitapp.ui.deploymentTime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.helper.DateHelper
import com.example.studienarbeitapp.helper.StorageHelper
import com.example.studienarbeitapp.models.request.RequestDeploymentTimeModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentTimeModel
import com.example.studienarbeitapp.services.DeploymentTimeService
import java.util.Date

/**
 * ViewModel responsible for managing deployment time.
 */
class DeploymentTimeViewModel(private val deploymentTimeService: DeploymentTimeService) :
    ViewModel() {

    // LiveData for the response of deployment time
    val deploymentTimeResponse = MutableLiveData<ResponseDeploymentTimeModel>()

    // LiveData for the request of deployment time
    val deploymentTimeRequest = MutableLiveData<RequestDeploymentTimeModel>().apply {
        value = RequestDeploymentTimeModel("", "", "", "", "", "")
    }

    /**
     * Fetches deployment time from the service.
     */
    fun getDeploymentTimeFromService() {
        deploymentTimeService.fetchDeploymentTime(
            onSuccess = { deploymentTimeResponse ->
                // Update LiveData with the fetched deployment time data
                this.deploymentTimeResponse.value = deploymentTimeResponse
            },
            onError = { emptyTimeResponse ->
                // Set empty response as value
                deploymentTimeResponse.value = emptyTimeResponse
            }
        )
    }

    /**
     * Sends deployment information to the service.
     */
    fun sendDeploymentInformation() {
        val deploymentTimeModel = StorageHelper.getTimeModelId()?.let {
            deploymentTimeRequest.value?.let { it1 ->
                RequestDeploymentTimeModel(
                    it,
                    it1.start,
                    deploymentTimeRequest.value!!.arrivalOnSite,
                    deploymentTimeRequest.value!!.patientAdmitted,
                    deploymentTimeRequest.value!!.arrivalOnSite2,
                    deploymentTimeRequest.value!!.end
                )
            }
        }
        if (deploymentTimeModel != null) {
            deploymentTimeService.sendDeploymentTimes(deploymentTimeModel)
        }
    }

    /**
     * Sets the start time for deployment.
     */
    fun setStartTime(date: Date) {
        deploymentTimeRequest.value!!.start = DateHelper.formatDate(date)
    }

    /**
     * Sets the arrival time on site for deployment.
     */
    fun setArrivalOnSite(date: Date) {
        deploymentTimeRequest.value!!.arrivalOnSite = DateHelper.formatDate(date)
    }

    /**
     * Sets the time when patient is admitted for deployment.
     */
    fun setPatientAdmitted(date: Date) {
        deploymentTimeRequest.value!!.patientAdmitted = DateHelper.formatDate(date)
    }

    /**
     * Sets the arrival time on site (second instance) for deployment.
     */
    fun setArrivalOnSite2(date: Date) {
        deploymentTimeRequest.value!!.arrivalOnSite2 = DateHelper.formatDate(date)
    }

    /**
     * Sets the end time for deployment.
     */
    fun setEndTime(date: Date) {
        deploymentTimeRequest.value!!.end = DateHelper.formatDate(date)
    }
}
