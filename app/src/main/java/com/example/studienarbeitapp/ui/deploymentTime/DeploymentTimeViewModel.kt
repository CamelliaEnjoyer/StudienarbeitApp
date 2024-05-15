package com.example.studienarbeitapp.ui.deploymentTime

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studienarbeitapp.helper.DateHelper
import com.example.studienarbeitapp.models.request.RequestDeploymentTimeModel
import com.example.studienarbeitapp.models.response.ResponseDeploymentTimeModel
import com.example.studienarbeitapp.services.DeploymentTimeService
import java.util.Date

class DeploymentTimeViewModel(private val deploymentTimeService: DeploymentTimeService) : ViewModel() {

    val deploymentTimeResponse = MutableLiveData<ResponseDeploymentTimeModel>()
    val deploymentTimeRequest = MutableLiveData<RequestDeploymentTimeModel>().apply {
        value = RequestDeploymentTimeModel("", "", "", "", "")
    }

    fun getDeploymentTimeFromService() {
        if(deploymentTimeResponse.value == null){
            deploymentTimeService.fetchDeploymentTime(
                onSuccess = { deploymentTimeResponse ->
                    // Update LiveData with the fetched user data
                    this.deploymentTimeResponse.value = deploymentTimeResponse
                },
                onError = { emptyTimeResponse ->
                    // Set empty response as value
                    deploymentTimeResponse.value = emptyTimeResponse
                }
            )
        }
    }

    fun sendDeploymentInformation(context: Context, onSuccess: () -> Unit,
                                  onError: (String) -> Unit){
        val deploymentTimeModel = deploymentTimeRequest.value
        if (deploymentTimeModel != null) {
            deploymentTimeService.sendDeploymentInformation(deploymentTimeModel, onSuccess = {
                onSuccess()
            },
            onError = {
                onError(it)
            })
        }
    }

    fun setStartTime(date: Date) {
        deploymentTimeRequest.value!!.start = DateHelper.formatDate(date)
    }

    fun setArrivalOnSite(date: Date) {
        deploymentTimeRequest.value!!.arrivalOnSite = DateHelper.formatDate(date)
    }

    fun setPatientAdmitted(date: Date) {
        deploymentTimeRequest.value!!.patientAdmitted = DateHelper.formatDate(date)
    }

    fun setArrivalOnSite2(date: Date) {
        deploymentTimeRequest.value!!.arrivalOnSite2 = DateHelper.formatDate(date)
    }

    fun setEndTime(date: Date) {
        deploymentTimeRequest.value!!.end = DateHelper.formatDate(date)
    }
}